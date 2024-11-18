package com.mdd.front.service;

import cn.hutool.json.JSONObject;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.mdd.common.entity.user.UserPointsOrder;
import com.mdd.front.validate.PaymentValidate;
import com.mdd.front.validate.pay.WeChatPayValidate;
import com.mdd.front.vo.pay.PayStatusVo;
import com.mdd.front.vo.pay.PayWayListVo;

/**
 * 支付接口服务类
 */
public interface IPayService {

    /**
     * 支付方式
     *
     * @author fzr
     * @param from 场景
     * @param orderId 订单ID
     * @param terminal 终端
     * @return List<PayWayListedVo>
     */
    PayWayListVo payWay(String from, Integer orderId, Integer terminal);

    /**
     * 支付状态
     *
     * @author fzr
     * @param from 场景
     * @param orderId 订单ID
     * @return PayStatusVo
     */
    PayStatusVo payStatus(String from, Integer orderId);

    /**
     * 发起支付
     *
     * @param params 参数
     * @param terminal 终端
     * @return Object
     */
    Object prepay(PaymentValidate params, Integer terminal);

    /**
     * 支付回调处理
     *
     * @param attach 场景码
     * @param outTradeNo 订单编号
     * @param transactionId 流水号
     */
    void handlePaidNotify(String attach, String outTradeNo, String transactionId) throws WxPayException;


    /**
     * 微信支付
     * @param paymentValidate
     * @return
     */
    JSONObject weChatPay(WeChatPayValidate weChatPayValidate);

    /**
     * 微信支付
     * @param paymentValidate
     * @return
     */
    JSONObject payBack(WeChatPayValidate weChatPayValidate);


    /**
     * 查询订单
     * @param id
     * @return
     */
    UserPointsOrder selectOrder(String id);

    /**
     * 更新订单
     * @param userPointsOrder
     */

    void updateOrder (UserPointsOrder userPointsOrder, com.alibaba.fastjson.JSONObject jsonObject);
}
