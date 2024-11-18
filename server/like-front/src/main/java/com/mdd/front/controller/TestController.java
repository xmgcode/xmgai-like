package com.mdd.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.mdd.common.aop.NotLogin;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.util.HttpUtils;
import com.yungouos.pay.entity.WxOauthInfo;
import com.yungouos.pay.entity.WxWebLoginBiz;
import com.yungouos.pay.wxapi.WxApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@Api(tags = "文章管理")
public class TestController {


    @NotLogin
    @GetMapping("/getBiliBli")
    @ApiOperation(value="文章分类")
    public AjaxResult<Object> category() {
        String nonce = "5456413131123121";
        String timestamp = "1716344428234";
        String apiKey = "1232132131";
        String apiSecret = "88888888";
        String sign = createSign(nonce,timestamp,apiKey,apiSecret);
        String res = HttpUtils.sendGet("http://localhost:8082/api/center/getBiBiHot","nonce=" + nonce + "&timestamp=" + timestamp + "&apiKey=" + apiKey + "&sign=" + sign);
        JSONObject jsStr = JSONObject.parseObject(res);
        return AjaxResult.success(jsStr);
    }


    public static String createSign(String nonce,String timestamp,String apiKey,String apiSecret){
        Map<String,String> map = new HashMap<>();
        map.put("nonce",nonce);
        map.put("timestamp",timestamp);
        map.put("apiKey",apiKey);
        map.put("apiSecret",apiSecret);
        String jsonParams =  JSON.toJSONString(map);
        String sign = md5(jsonParams);
        return sign;

    }


    /**
     * MD5加密
     * @param input
     * @return
     */
    public static String md5(String input) {
        try {
            // 创建MD5加密对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 执行加密操作
            byte[] messageDigest = md.digest(input.getBytes());
            // 将字节数组转换为16进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            // 返回加密后的字符串
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) {
        String mch_id = "100254502";
        String key = "AE9CF0E6B82E4B5AB2D1C85A6F4BA270";
        String callback_url = "http://www.baidu.com";
        JSONObject params = new JSONObject();
        params.put("desc", "附加数据，授权结束后可以返回");
        params.put("test", "你可以组装任何你想临时存储的数据");

        /**
         * 授权类型 mp-base：基础授权，不会有授权页面，用户无感知，可获取openid。
         *
         * mp-info：详细授权，首次授权会弹出授权页面，可获取用户昵称、头像等信息。
         *
         * open-url：微信PC端扫码登录url
         */
        String type = "mp-base";
        String oauthUrl = WxApi.getWxOauthUrl(mch_id, callback_url, type, params, key);
        System.out.println(oauthUrl);

        String code = "2EBBFC55DBFBAD16501D9CEF20D93F15";
        WxOauthInfo wxOauthInfo = WxApi.getWxOauthInfo(mch_id, code, key);
        System.out.println(wxOauthInfo);

        WxWebLoginBiz wxWebLoginBiz = WxApi.getWebLogin(mch_id, callback_url, params, key);

        System.out.println(wxWebLoginBiz.toString());
    }

}
