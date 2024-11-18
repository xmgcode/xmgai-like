package com.mdd.front.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.mdd.common.aop.NotLogin;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.entity.RechargeOrder;
import com.mdd.common.entity.user.UserPointsOrder;
import com.mdd.common.enums.ClientEnum;
import com.mdd.common.enums.PaymentEnum;
import com.mdd.common.exception.OperateException;
import com.mdd.common.mapper.RechargeOrderMapper;
import com.mdd.common.mapper.user.UserPointsOrderMapper;
import com.mdd.common.plugin.wechat.WxPayDriver;
import com.mdd.common.util.SnowIdUtil;
import com.mdd.common.util.StringUtils;
import com.mdd.common.util.YmlUtils;
import com.mdd.front.LikeFrontThreadLocal;
import com.mdd.front.service.IPayService;
import com.mdd.front.validate.PaymentValidate;
import com.mdd.front.vo.pay.PayStatusVo;
import com.mdd.front.vo.pay.PayWayListVo;
import com.yungouos.pay.util.PaySignUtil;
import com.yungouos.pay.wxpay.WxPay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/pay")
@Api(tags = "支付管理")
@Slf4j
public class PayController {

    @Resource
    RechargeOrderMapper rechargeOrderMapper;

    @Resource
    IPayService iPayService;

    @Resource
    UserPointsOrderMapper userPointsOrderMapper;

    @GetMapping("/payWay")
    @ApiOperation("支付方式")
    public AjaxResult<PayWayListVo> payWay(@Validated @NotNull(message = "from参数丢失") @RequestParam String from,
                                           @Validated @NotNull(message = "orderId参数丢失") @RequestParam Integer orderId) {
        Integer terminal = LikeFrontThreadLocal.getTerminal();

        PayWayListVo list = iPayService.payWay(from, orderId, terminal);
        return AjaxResult.success(list);
    }

    @GetMapping("/payStatus")
    @ApiOperation(("支付状态"))
    public AjaxResult<PayStatusVo> payStatus(@Validated @NotNull(message = "from参数丢失") @RequestParam String from,
                                             @Validated @NotNull(message = "orderId参数丢失") @RequestParam Integer orderId) {
        PayStatusVo vo = iPayService.payStatus(from, orderId);
        return AjaxResult.success(vo);
    }

    @PostMapping("/prepay")
    @ApiOperation("发起支付")
    public AjaxResult<Object> prepay(@Validated @RequestBody PaymentValidate requestObj) {
        // 接收参数
        String scene     = requestObj.getScene();
        Integer payWay   = requestObj.getPayWay();
        Integer orderId  = requestObj.getOrderId();
        Integer terminal = LikeFrontThreadLocal.getTerminal();

        // 订单处理
        int payStatus = 0;
        switch (scene) {
            case "recharge":
                RechargeOrder rechargeOrder = rechargeOrderMapper.selectById(orderId);

                Assert.notNull(rechargeOrder, "订单不存在");
                Assert.isTrue(!payWay.equals(PaymentEnum.WALLET_PAY.getCode()), "支付类型不被支持");

                requestObj.setUserId(rechargeOrder.getUserId());
                requestObj.setOutTradeNo(rechargeOrder.getOrderSn());
                requestObj.setOrderAmount(rechargeOrder.getOrderAmount());
                requestObj.setDescription("余额充值");
                requestObj.setAttach("recharge");
                payStatus = rechargeOrder.getPayStatus();

                rechargeOrder.setPayWay(payWay);
                rechargeOrderMapper.updateById(rechargeOrder);
                break;
            case "order":
                break;
        }

        // 订单校验
        if (payStatus != 0) {
            throw new OperateException("订单已支付");
        }

        // 发起支付
        Object result = iPayService.prepay(requestObj, terminal);
        return AjaxResult.success(result);
    }

    @NotLogin
    @PostMapping("/notifyMnp")
    @ApiOperation("微信支付回调")
    public AjaxResult<Object> notifyMnp(@RequestBody String jsonData, HttpServletRequest request) throws WxPayException {
        // 构建签名
        SignatureHeader signatureHeader = new SignatureHeader();
        signatureHeader.setSignature(request.getHeader("wechatpay-signature"));
        signatureHeader.setNonce(request.getHeader("wechatpay-nonce"));
        signatureHeader.setSerial(request.getHeader("wechatpay-serial"));
        signatureHeader.setTimeStamp(request.getHeader("wechatpay-timestamp"));

        // 解密数据
        WxPayService wxPayService = WxPayDriver.handler(ClientEnum.MNP.getCode());
        WxPayOrderNotifyV3Result.DecryptNotifyResult notifyResult = wxPayService.parseOrderNotifyV3Result(jsonData, signatureHeader).getResult();

        // 取出数据
        String transactionId = notifyResult.getTransactionId();
        String outTradeNo = notifyResult.getOutTradeNo();
        String attach =  notifyResult.getAttach();

        // 处理回调
        iPayService.handlePaidNotify(attach, outTradeNo, transactionId);
        return AjaxResult.success();
    }

    /***********************************以下是yungouOS支付******************************************/


    /**
     * 发起支付，返回二维码
     * @param comboType
     * @return
     */
    @GetMapping("/wxPayCode")
    @ApiOperation(value="获取API套餐")
    public AjaxResult<Object> wxPayCode(@RequestParam("comboType") String comboType) {

        Integer userIdInt = LikeFrontThreadLocal.getUserId();
        String userId = String.valueOf(userIdInt);

        /************点数兑换规则为1毛钱一个**************/
        if(StringUtils.isEmpty(comboType)){
            AjaxResult.failed("套餐类型不能为空！");
        }
        String mch_id = YmlUtils.get("yungouos.mch_id");
        String payKey = YmlUtils.get("yungouos.paykey");
        String returnUrl = YmlUtils.get("yungouos.pay_returnUrl");
        String appid = YmlUtils.get("yungouos.appid");
        // 收银台支付结束后返回地址
        String notify_url = YmlUtils.get("yungouos.notify_url");
        //订单号，必须唯一
        String out_trade_no = SnowIdUtil.getStrUUid();
        String total_fee = "";
        String body = "";
        JSONObject attach = new JSONObject();
        attach.put("comboType",comboType);
        attach.put("userId",userId);
        attach.put("out_trade_no",out_trade_no);
        String attathNew = attach.toString();

        switch (comboType){
            case "comboOne":
                total_fee = "9.9";
                body = "点数充值：套餐一";
                break;
            case "comboTwo":
                total_fee = "29.9";

                body = "点数充值：套餐二";
                break;
            case "comboThree":
                total_fee = "99.9";
                body = "点数充值：套餐三";
                break;
            default:
                // 如果comboType不是预期的任何一个值，则返回错误信息或抛出异常
                return AjaxResult.failed("参数错误！");
        }

        //测试时打开
//        total_fee = "0.01";
        //扫码支付 返回二维码连接
        String result = WxPay.nativePay(out_trade_no, total_fee, mch_id, body, null, appid, attathNew, notify_url, returnUrl, null, null, null, null, payKey);
        //新增订单信息
        UserPointsOrder userPointsOrder = new UserPointsOrder();
        userPointsOrder.setId(out_trade_no);
        BigDecimal totalfeeDc = new BigDecimal(total_fee);
        userPointsOrder.setMoney(totalfeeDc);
        userPointsOrder.setCreateTime(new Date());
        userPointsOrder.setUserId(userId);
        userPointsOrder.setPayStatus("0");
        userPointsOrderMapper.insert(userPointsOrder);
        log.info("生成的支付二维码为：" + result);
        return AjaxResult.success("支付二维码生成成功！",result);
    }



//    @NotLogin
//    @PostMapping("/payBack")
//    @ApiOperation("微信支付回调")
//    public AjaxResult<Object> payBack(@RequestBody WeChatPayReturnValidate weChatPayReturnValidate) {
//        System.out.println(weChatPayReturnValidate);
//        // 处理回调
//        return AjaxResult.success();
//    }


    /**
     * 回调时间15s/15s/30s/3m/10m/20m/30m/30m/30m/60m/3h/3h/3h/6h/6h - 总计 24h4m）
     * 详情：https://open.pay.yungouos.com/#/callback/notify
     * @param data
     * @param request
     * @param response
     * @return
     */
    @NotLogin
    @PostMapping("/payBack")
    public String notify(@RequestParam Map<String, String> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            System.out.println("接受到支付结果回调");
            System.out.println(data.toString());

            String payChannel = data.get("payChannel");

            String attach = data.get("attach");

            JSONObject attachObj = JSONObject.parseObject(attach);

            if (StrUtil.isBlank(payChannel)) {
                return "payChannel is not null";
            }

            String key = YmlUtils.get("yungouos.paykey");

            boolean sign = PaySignUtil.checkNotifySign(request, key);

            System.out.println("签名验证：" + sign);

            if (!sign) {
                return "sign fail";
            }

            String outTradeNo = data.get("outTradeNo");
            String payNo = data.get("payNo");
            String time = data.get("time");
            String code = data.get("code");

            if (Integer.valueOf(code).intValue() != 1) {
                return "pay fail";
            }

            UserPointsOrder pointsOrder = iPayService.selectOrder(outTradeNo);
            if(null == pointsOrder){
                return "fail";
            }else {
                pointsOrder.setPayNo(payNo);
                pointsOrder.setPayTime(time);
                pointsOrder.setUpdateTime(new Date());
                pointsOrder.setPayStatus("1");
                //更新订单、充值记录、点数信息
                iPayService.updateOrder(pointsOrder,attachObj);
                log.info("更新点数相关信息成功！");
            }
            PrintWriter out = response.getWriter();
            out.print("SUCCESS");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



}
