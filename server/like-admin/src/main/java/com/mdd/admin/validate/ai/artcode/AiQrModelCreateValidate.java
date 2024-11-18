package com.mdd.admin.validate.ai.artcode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel("艺术二维码模板创建参数")
public class AiQrModelCreateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "提示词")
    private String prompt;

    @NotNull(message = "modelNum参数缺失")
    @ApiModelProperty(value = "模板序号")
    private Integer template_id;

    @NotNull(message = "回调地址参数缺失")
    @ApiModelProperty(value = "回调地址")
    private String callback_url;

    @NotNull(message = "图片base64缺失")
    @ApiModelProperty(value = "回调地址")
    private String qr_image;


}
