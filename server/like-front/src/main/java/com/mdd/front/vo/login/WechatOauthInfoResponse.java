package com.mdd.front.vo.login;

import lombok.Data;

/**
 * 微信登录授权信息
 */
@Data
public class WechatOauthInfoResponse {
    /**
     * 消息
     */
    private String msg;

    /**
     * code
     */
    private int code;

    /**
     * 用户数据
     */
    private WechatOauthInfoData data;

}