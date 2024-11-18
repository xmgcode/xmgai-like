package com.mdd.admin.validate.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Date;
import javax.validation.constraints.*;

@Data
@ApiModel("充值记录创建参数")
public class UserPointsChargrecordCreateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "money参数缺失")
    @ApiModelProperty(value = "充值金额")
    private BigDecimal money;

    @NotNull(message = "obtainPoints参数缺失")
    @ApiModelProperty(value = "获取点数")
    private BigDecimal obtainPoints;

}
