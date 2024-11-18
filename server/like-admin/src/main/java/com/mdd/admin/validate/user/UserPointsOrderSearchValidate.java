package com.mdd.admin.validate.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Date;

@Data
@ApiModel("点数订单搜素参数")
public class UserPointsOrderSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal money;

    @ApiModelProperty(value = "支付单号（第三方支付单号）")
    private String payNo;

    @ApiModelProperty(value = "支付状态（0未支付，1已支付）")
    private String payStatus;

    @ApiModelProperty(value = "开始时间")
    private String createTimeStart;

    @ApiModelProperty(value = "结束时间")
    private String createTimeEnd;

}
