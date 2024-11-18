package com.mdd.admin.validate.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Date;

@Data
@ApiModel("充值记录搜素参数")
public class UserPointsChargrecordSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal money;

    @ApiModelProperty(value = "获取点数")
    private BigDecimal obtainPoints;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "开始时间")
    private String createTimeStart;

    @ApiModelProperty(value = "结束时间")
    private String createTimeEnd;

}
