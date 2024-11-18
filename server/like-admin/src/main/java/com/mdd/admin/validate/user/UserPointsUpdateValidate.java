package com.mdd.admin.validate.user;

import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户点数参数
 * @author xmg
 */
@Data
@ApiModel("用户点数更新参数")
public class UserPointsUpdateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @IDMust(message = "id参数必传")
    @ApiModelProperty(value = "主键")
    private String id;

    @NotNull(message = "totalPoints参数缺失")
    @ApiModelProperty(value = "总点数")
    private BigDecimal totalPoints;

    @NotNull(message = "remainPoints参数缺失")
    @ApiModelProperty(value = "剩余点数")
    private BigDecimal remainPoints;

}
