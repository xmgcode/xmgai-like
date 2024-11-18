package com.mdd.admin.validate.ai.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel("艺术二维码模板创建参数")
public class AiArtcodeModelCreateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "modelNum参数缺失")
    @ApiModelProperty(value = "模板序号")
    private Long modelNum;

    @NotNull(message = "name参数缺失")
    @ApiModelProperty(value = "模板名称")
    private String name;

    @NotNull(message = "previewImg参数缺失")
    @ApiModelProperty(value = "预览地址")
    private String previewImg;

}
