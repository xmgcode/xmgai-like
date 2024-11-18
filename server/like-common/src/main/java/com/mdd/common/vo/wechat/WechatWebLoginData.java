package com.mdd.common.vo.wechat;

import lombok.Data;

/**
 * 微信登录链接数据
 */
@Data
public class WechatWebLoginData {
    /**
     * 微信APPID
     */
    private String appId;

    /**
     * 应用授权作用域
     */
    private String scope;

    /**
     * 交互code
     */
    private String state;

    /**
     * 授权回调url
     */
    private String redirect_uri;


}