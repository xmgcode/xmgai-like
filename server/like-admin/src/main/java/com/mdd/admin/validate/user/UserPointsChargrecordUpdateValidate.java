package com.mdd.admin.validate.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Date;
import com.mdd.common.validator.annotation.IDMust;

/**
 * 充值记录参数
 * @author xmg
 */
@Data
@ApiModel("充值记录更新参数")
public class UserPointsChargrecordUpdateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @IDMust(message = "id参数必传")
    @ApiModelProperty(value = "主键")
    private String id;

    @NotNull(message = "money参数缺失")
    @ApiModelProperty(value = "充值金额")
    private BigDecimal money;

    @NotNull(message = "obtainPoints参数缺失")
    @ApiModelProperty(value = "获取点数")
    private BigDecimal obtainPoints;

}
