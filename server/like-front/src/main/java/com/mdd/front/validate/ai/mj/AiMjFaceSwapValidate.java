package com.mdd.front.validate.ai.mj;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel("AI配置创建参数")
public class AiMjFaceSwapValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "调用模式")
    private String mode;

    @NotNull(message = "人脸源图片缺失")
    @ApiModelProperty(value = "人脸源图片base64")
    private String sourceBase64;

    @NotNull(message = "目标图片缺失")
    @ApiModelProperty(value = "目标图片base64")
    private String targetBase64;


}
