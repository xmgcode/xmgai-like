package com.mdd.front.vo.login;

import lombok.Data;

/**
 * 微信登录用户基础信息
 */
@Data
public class WechatOauthInfo {
    /**
     * 所在城市（2021-10-20日起微信不再返回该值）
     */
    private String city;

    /**
     *
     * 所在国家（2021-10-20日起微信不再返回该值）
     */
    private String country;

    /**
     * 用户头像
     */
    private String headimgurl;

    /**
     * 语言
     */
    private String language;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户openId
     */
    private String openid;

    /**
     *
     * 所在省份（2021-10-20日起微信不再返回该值）
     */
    private String province;

    /**
     *
     * 性别（2021-10-20日起强制返回0）
     */
    private String sex;

    /**
     * 用户unionid，同一个微信号在YunGouOS内unionid唯一
     */
    private String unionid;

    private int band;

}