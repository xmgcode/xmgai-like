package com.mdd.front.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.mdd.common.aop.NotLogin;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.entity.user.User;
import com.mdd.common.util.HttpClientMyUtil;
import com.mdd.common.util.StringUtils;
import com.mdd.common.util.ToolUtils;
import com.mdd.common.util.YmlUtils;
import com.mdd.common.vo.wechat.WechatWebLoginData;
import com.mdd.common.vo.wechat.WechatWebLoginResponse;
import com.mdd.front.LikeFrontThreadLocal;
import com.mdd.front.service.ILoginService;
import com.mdd.front.validate.login.*;
import com.mdd.front.vo.login.LoginTokenVo;
import com.mdd.front.vo.login.LoginUrlsVo;
import com.mdd.front.vo.login.WechatOauthInfoResponse;
import com.yungouos.pay.common.PayException;
import com.yungouos.pay.util.PaySignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/login")
@Api(tags = "登录管理")
public class LoginController {


    @Resource
    ILoginService iLoginService;

    @NotLogin
    @PostMapping("/register")
    @ApiOperation(value="注册账号")
    public AjaxResult<Object> register(@Validated @RequestBody RegisterValidate registerValidate) {
        Integer terminal = LikeFrontThreadLocal.getTerminal();
        String username = registerValidate.getUsername();
        String password = registerValidate.getPassword();

        iLoginService.register(username, password, terminal);
        return AjaxResult.success();
    }

    @NotLogin
    @PostMapping("/accountLogin")
    @ApiOperation(value="账号登录")
    public AjaxResult<LoginTokenVo> accountLogin(@Validated @RequestBody LoginPwdValidate loginPwdValidate) {
        Integer terminal = LikeFrontThreadLocal.getTerminal();
        String username = loginPwdValidate.getUsername();
        String password = loginPwdValidate.getPassword();

        LoginTokenVo vo = iLoginService.accountLogin(username, password, terminal);
        return AjaxResult.success(vo);
    }

    @NotLogin
    @PostMapping("/mobileLogin")
    @ApiOperation(value="手机登录")
    public AjaxResult<LoginTokenVo> mobileLogin(@Validated @RequestBody LoginPhoneValidate loginPhoneValidate) {
        Integer terminal = LikeFrontThreadLocal.getTerminal();
        String mobile = loginPhoneValidate.getMobile();
        String code = loginPhoneValidate.getCode();

        LoginTokenVo vo = iLoginService.mobileLogin(mobile, code, terminal);
        return AjaxResult.success(vo);
    }


    @NotLogin
    @PostMapping("/validatePhone")
    @ApiOperation(value="校验手机号是否存在")
    public AjaxResult<Object> validatePhone(@RequestBody LoginOnlyPhoneValidate loginOnlyPhoneValidate) {
        String mobile = loginOnlyPhoneValidate.getMobile();
        User vo = iLoginService.validatePhone(mobile);
        if(null == vo){
            return AjaxResult.failed("手机号不存在,请先注册！");
        }else{
            return AjaxResult.success(vo);
        }
    }

    @NotLogin
    @PostMapping("/mnpLogin")
    @ApiOperation(value="微信登录")
    public AjaxResult<LoginTokenVo> mnpLogin(@Validated @RequestBody LoginCodeValidate loginCodeValidate) {
        Integer terminal = LikeFrontThreadLocal.getTerminal();
        String code = loginCodeValidate.getCode();

        LoginTokenVo vo = iLoginService.mnpLogin(code, terminal);
        return AjaxResult.success(vo);
    }

    @NotLogin
    @PostMapping("/oaLogin")
    @ApiOperation(value="公众号登录")
    public AjaxResult<LoginTokenVo> oaLogin(@Validated @RequestBody LoginCodeValidate loginCodeValidate) {
        Integer terminal = LikeFrontThreadLocal.getTerminal();
        String code = loginCodeValidate.getCode();

        LoginTokenVo vo = iLoginService.officeLogin(code, terminal);
        return AjaxResult.success(vo);
    }

    @NotLogin
    @GetMapping("/oaCodeUrl")
    @ApiOperation(value="公众号链接")
    public AjaxResult<LoginUrlsVo> oaCodeUrl(@Validated @NotNull() @RequestParam("url") String url) {
        LoginUrlsVo vo = new LoginUrlsVo();
        vo.setUrl(iLoginService.oaCodeUrl(url));
        return AjaxResult.success(vo);
    }

    @NotLogin
    @GetMapping("/scanCodeUrl")
    @ApiOperation(value="PC扫码链接")
    public AjaxResult<LoginUrlsVo> scanCodeUrl(@Validated @NotNull() @RequestParam("url") String url, HttpSession session) {
        String qrcodeUrl = iLoginService.scanCodeUrl(url, session);
        LoginUrlsVo vo = new LoginUrlsVo();
        vo.setUrl(qrcodeUrl);
        return AjaxResult.success(vo);
    }

    @NotLogin
    @PostMapping("/scanLogin")
    @ApiOperation(value="PC扫码登录")
    public AjaxResult<Object> scanLogin(@Validated @RequestBody LoginScanValidate loginScanValidate, HttpSession session) {
        Integer terminal = LikeFrontThreadLocal.getTerminal();
        String code = loginScanValidate.getCode();
        String state = loginScanValidate.getState();

        LoginTokenVo vo = iLoginService.scanLogin(code, state, terminal, session);
        return AjaxResult.success(vo);
    }




    @NotLogin
    @ApiOperation(value = "获取微信登录链接", notes = "获取微信登录链接")
    @PostMapping(value="/getWeChatLoginUrl")
    public AjaxResult<Object> getWeChatLoginUrl(@RequestBody String operateType, HttpSession session) {
        String payKey = YmlUtils.get("yungouos.paykey");
        String mch_id = YmlUtils.get("yungouos.mch_id");
        String callback_url = "http://localhost:3000";
        String type = "mp-base";

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, String> resultEn = new HashMap<>();
        try {
            if (StrUtil.isBlank(mch_id)) {
                throw new PayException("mch_id不能为空！");
            }
            if (StrUtil.isBlank(callback_url)) {
                throw new PayException("callback_url不能为空！");
            }
            if (StrUtil.isBlank(payKey)) {
                throw new PayException("key不能为空！");
            }
            map.put("mch_id", mch_id);
            map.put("callback_url", callback_url);

            // 上述必传参数签名
            String sign = PaySignUtil.createSign(map, payKey);
            map.put("sign", sign);
            if (!StrUtil.isBlank(type)) {
                map.put("type", type);
            }

            JSONObject params = new JSONObject();
            if (params != null) {
                map.put("params", params.toJSONString());
            }

            String result = HttpClientMyUtil.DoPost("https://api.wx.yungouos.com/api/wx/getWebLogin", map);
            WechatWebLoginResponse wechatWebLogin = ToolUtils.jsonToPojo(result, WechatWebLoginResponse.class);
            WechatWebLoginData wechatWebLoginData = wechatWebLogin.getData();

            if (StrUtil.isBlank(result)) {
                throw new PayException("API接口返回为空，请联系客服");
            }

            // 构建返回参数
            resultEn.put("appId", wechatWebLoginData.getAppId());
            resultEn.put("scope", wechatWebLoginData.getScope());
            resultEn.put("state", wechatWebLoginData.getState());
            resultEn.put("redirect_uri", wechatWebLoginData.getRedirect_uri());

        } catch (PayException e) {
            e.printStackTrace();
            throw new PayException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new PayException(e.getMessage());
        }
        return AjaxResult.success(resultEn);
    }




    /**
     * 回调获取yunos的code，并完成登录返回token
     * 微信登录回调函数
     * 参考接入文档：https://open.pay.yungouos.com/#/api/api/wx/getOauthInfo
     * @param code
     * @return
     */
    @ApiOperation(value="获取yunos的code")
    @NotLogin
    @GetMapping(value = "/wechatLoginCallback")
    public AjaxResult<Object> getWechatOauthInfo(@RequestParam("code") String code) {
        String payKey = YmlUtils.get("yungouos.paykey");
        String mch_id = YmlUtils.get("yungouos.mch_id");
        if (StringUtils.isEmpty(code)) {
            return AjaxResult.failed("微信登录回调失败！");
        }

        // 额外参数
        Map<String, Object> params = new HashMap<>();
        params.put("mch_id", mch_id);
        params.put("code", code);
        params.put("sign", PaySignUtil.createSign(params, payKey));
        String result = HttpClientMyUtil.DoGet("https://api.wx.yungouos.com/api/wx/getOauthInfo", params);
        WechatOauthInfoResponse wechatOauthInfoResponse = ToolUtils.jsonToPojo(result, WechatOauthInfoResponse.class);
        //用户操作信息
        LoginTokenVo loginTokenVo = iLoginService.getWechatOauthInfo(wechatOauthInfoResponse.getData().getWxUserInfo());
        return AjaxResult.success(loginTokenVo.getToken());
    }


    public WechatWebLoginData getXyWeChatLoginUrl(String code) {
        String wechatCallbackUrl = "http://localhost:3000";
        String payKey = YmlUtils.get("yungouos.paykey");
        String mch_id = YmlUtils.get("yungouos.mch_id");

        if (StringUtils.isEmpty(wechatCallbackUrl)) {
            AjaxResult.success("后台暂未配置回调地址");
        }
        // 额外参数
        Map<String, Object> params = new HashMap<>();
        params.put("mch_id", mch_id);
        params.put("callback_url", wechatCallbackUrl);
        String sign = PaySignUtil.createSign(params, payKey);
        params.put("sign", sign);
        Map<String, Object> otherParams = new HashMap<>();
        otherParams.put("state", code);

        params.put("params", ToolUtils.objectToJson(otherParams));
        String result = HttpClientMyUtil.DoPost("https://api.wx.yungouos.com/api/wx/getWebLogin", params);
        WechatWebLoginResponse wechatWebLoginResponse = ToolUtils.jsonToPojo(result, WechatWebLoginResponse.class);
        if (wechatWebLoginResponse == null || wechatWebLoginResponse.getCode() != 0) {
            //"获取微信登录链接失败"
            return null;
        }
        return wechatWebLoginResponse.getData();
    }




    @NotLogin
    @GetMapping("/getWxInfo")
    @ApiOperation(value="获取yunos的code")
    public AjaxResult<Object> getWxInfo(@RequestParam("code") String code,HttpSession session) {
        dealLogin(code,session);
        return AjaxResult.success("");
    }


    public AjaxResult<Object> dealLogin(String code, HttpSession session) {
        Integer terminal = LikeFrontThreadLocal.getTerminal();
        String state = "";
        LoginTokenVo vo = iLoginService.scanLogin(code, state, terminal, session);
        return AjaxResult.success(vo);
    }






}
