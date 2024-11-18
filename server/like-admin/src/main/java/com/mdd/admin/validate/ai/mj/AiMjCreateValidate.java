package com.mdd.admin.validate.ai.mj;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel("AI配置创建参数")
public class AiMjCreateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "调用模式")
    private String mode;

    @ApiModelProperty(value = "垫图base64数组")
    private String[] base64Array;

    @NotNull(message = "提示词缺失！")
    @ApiModelProperty(value = "prompt")
    private String prompt;


}
