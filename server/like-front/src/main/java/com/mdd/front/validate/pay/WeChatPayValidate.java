package com.mdd.front.validate.pay;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel("预支付订单参数")
public class WeChatPayValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "订单号（不可重复）缺失")
    @ApiModelProperty("订单号（不可重复）")
    private String out_trade_no;

    @NotNull(message = "支付金额缺失")
    @ApiModelProperty("支付金额（范围：0.01~999999）单位：元")
    private String total_fee;

    @NotNull(message = "微信支付商户号参数缺失")
    @ApiModelProperty("微信支付商户号")
    private String mch_id;

    @NotNull(message = "购买的商品或服务参数缺失")
    @ApiModelProperty(value = "购买的商品或服务")
    private String body;

    @ApiModelProperty(value = "返回类型", notes = "返回类型（1、返回微信原生的支付连接需要自行生成二维码；2、直接返回付款二维码地址，页面上展示即可。不填默认1 ）")
    private String type;

    @ApiModelProperty(value = "YunGouOS平台报备的app_id", notes = "YunGouOS平台报备的app_id，不传则默认为商户号开户时场景。商户号登记了多场景情况下必传该参数")
    private String app_id;

    @ApiModelProperty(value = "附加数据", notes = "附加数据，回调时候原路返回")
    private String attach;

    @ApiModelProperty(value = "异步回调地址", notes = "异步回调地址，用户支付成功后系统将会把支付结果发送到该地址，不填则无回调。")
    private String notify_url;

    @ApiModelProperty(value = "同步地址", notes = "同步地址（收银台模式才有效）。支付完毕后用户浏览器返回到该地址")
    private String return_url;

    @ApiModelProperty(value = "分账模式", notes = "分账模式。【0：不分账 1：自动分账 2：手动分账】 默认 0")
    private String auto;

    @ApiModelProperty(value = "执行自动分账动作的节点", notes = "执行自动分账动作的节点，枚举值【pay、callback】分别表示【付款成功后分账、回调成功后分账】（收银台模式才有效）。支付完毕后用户浏览器返回到该地址")
    private String auto_node;

    @ApiModelProperty(value = "分账配置单号", notes = "分账配置单号。支持多个分账，使用,号分割")
    private String config_no;

    @ApiModelProperty(value = "附加业务参数", notes = "附加业务参数")
    private JSONObject biz_params;

    @ApiModelProperty(value = "数据签名", notes = "数据签名")
    private String sign;

}
