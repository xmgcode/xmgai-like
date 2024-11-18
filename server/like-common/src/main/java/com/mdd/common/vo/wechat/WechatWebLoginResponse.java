package com.mdd.common.vo.wechat;

import lombok.Data;

/**
 * 微信登录链接获取
 */
@Data
public class WechatWebLoginResponse {
    /**
     * 消息
     */
    private String msg;

    /**
     * code
     */
    private int code;

    /**
     * 登录返回数据
     */
    private WechatWebLoginData data;
}