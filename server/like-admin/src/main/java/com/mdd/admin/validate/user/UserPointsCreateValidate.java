package com.mdd.admin.validate.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel("用户点数创建参数")
public class UserPointsCreateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "totalPoints参数缺失")
    @ApiModelProperty(value = "总点数")
    private BigDecimal totalPoints;

    @NotNull(message = "remainPoints参数缺失")
    @ApiModelProperty(value = "剩余点数")
    private BigDecimal remainPoints;

}
