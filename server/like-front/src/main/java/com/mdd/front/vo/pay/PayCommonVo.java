package com.mdd.front.vo.pay;

import lombok.Data;

/**
 * 微信登录链接获取
 */
@Data
public class PayCommonVo {
    /**
     * 消息
     */
    private String msg;

    /**
     * 返回数据（二维码地址或微信的支付连接【取决于传递的type类型】）
     */
    private String data;

    /**
     * 状态【0：成功；1：失败】
     */
    private String code;
}