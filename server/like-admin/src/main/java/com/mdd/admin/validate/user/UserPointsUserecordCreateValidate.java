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
@ApiModel("使用记录创建参数")
public class UserPointsUserecordCreateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "scene参数缺失")
    @ApiModelProperty(value = "使用场景")
    private String scene;

    @NotNull(message = "consume参数缺失")
    @ApiModelProperty(value = "消耗点数")
    private BigDecimal consume;

}
