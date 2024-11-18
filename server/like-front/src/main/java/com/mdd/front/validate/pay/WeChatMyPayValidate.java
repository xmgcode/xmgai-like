package com.mdd.front.validate.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel("预支付订单参数")
public class WeChatMyPayValidate implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotNull(message = "订单号（不可重复）缺失")
    @ApiModelProperty("订单号（不可重复）")
    private String out_trade_no;

    @NotNull(message = "支付金额缺失")
    @ApiModelProperty("支付金额（范围：0.01~999999）单位：元")
    private String total_fee;

    @NotNull(message = "点数缺失")
    @ApiModelProperty("点数")
    private String amount;


}
