package com.mdd.admin.validate.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 使用记录参数
 * @author xmg
 */
@Data
@ApiModel("使用记录更新参数")
public class UserPointsUserecordUpdateValidate implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotNull(message = "主键参数缺失")
    @ApiModelProperty(value = "主键")
    private String id;

    @NotNull(message = "scene参数缺失")
    @ApiModelProperty(value = "使用场景")
    private String scene;

    @NotNull(message = "consume参数缺失")
    @ApiModelProperty(value = "消耗点数")
    private BigDecimal consume;

}
