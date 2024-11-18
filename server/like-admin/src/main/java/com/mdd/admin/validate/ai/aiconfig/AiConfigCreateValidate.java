package com.mdd.admin.validate.ai.aiconfig;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel("AI配置创建参数")
public class AiConfigCreateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

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
