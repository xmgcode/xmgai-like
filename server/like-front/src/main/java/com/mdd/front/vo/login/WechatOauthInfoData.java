package com.mdd.front.vo.login;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 微信登录用户数据
 */
@Data
public class WechatOauthInfoData {
    /**
     * 用户openId
     */
    private String openId;

    /**
     * 额外参数
     */
    private JSONObject params;

    /**
     * 微信用户信息
     */
    private WechatOauthInfo wxUserInfo;

    /**
     * 回调的url
     */
    private String callBackUrl;

}

