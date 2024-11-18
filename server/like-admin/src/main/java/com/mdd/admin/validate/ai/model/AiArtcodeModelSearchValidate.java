package com.mdd.admin.validate.ai.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("艺术二维码模板搜素参数")
public class AiArtcodeModelSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模板序号")
    private Long modelNum;

    @ApiModelProperty(value = "模板名称")
    private String name;

}
