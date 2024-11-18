package com.mdd.front.validate.pay;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("预支付订单参数")
public class WeChatPayReturnValidate implements Serializable {


    /***
     *  回调见参数https://open.pay.yungouos.com/#/callback/notify
     * ***/

    private static final long serialVersionUID = 1L;


    private String code;

    private String orderNo;

    private String outTradeNo;

    private String payNo;

    private String money;

    private String mchId;

    private String payChannel;

    private String time;

    private String attach;

    private String openId;

    private String payBank;

    private String sign;

}
