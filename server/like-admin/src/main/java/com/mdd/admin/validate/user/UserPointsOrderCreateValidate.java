package com.mdd.admin.validate.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel("点数订单创建参数")
public class UserPointsOrderCreateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "money参数缺失")
    @ApiModelProperty(value = "充值金额")
    private BigDecimal money;

    @NotNull(message = "payNo参数缺失")
    @ApiModelProperty(value = "支付单号（第三方支付单号）")
    private String payNo;

    @NotNull(message = "payStatus参数缺失")
    @ApiModelProperty(value = "支付状态（0未支付，1已支付）")
    private String payStatus;

}
