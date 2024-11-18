package com.mdd.front.validate.ai.mj;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel("Dalle3创建参数")
public class AiDalle3CreateValidate implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotNull(message = "图片尺寸不能为空")
    @ApiModelProperty(value = "图片尺寸")
    private String size;


    @NotNull(message = "所需图像的文本描述不能为空！")
    @ApiModelProperty(value = "所需图像的文本描述")
    private String prompt;


}
