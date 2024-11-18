package com.mdd.admin.validate.ai.aiconfig;

import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * AI配置参数
 * @author xmg
 */
@Data
@ApiModel("AI配置更新参数")
public class AiConfigUpdateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @IDMust(message = "id参数必传")
    @ApiModelProperty(value = "主键")
    private String id;

    @NotNull(message = "type参数缺失")
    @ApiModelProperty(value = "类型")
    private String type;

    @NotNull(message = "name参数缺失")
    @ApiModelProperty(value = "键")
    private String name;

    @NotNull(message = "value参数缺失")
    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "备注")
    private String remark;

}
