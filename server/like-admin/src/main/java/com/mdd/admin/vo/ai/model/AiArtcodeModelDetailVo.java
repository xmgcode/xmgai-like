package com.mdd.admin.vo.ai.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("艺术二维码模板详情Vo")
public class AiArtcodeModelDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "模板序号")
    private Long modelNum;

    @ApiModelProperty(value = "模板名称")
    private String name;

    @ApiModelProperty(value = "预览地址")
    private String previewImg;


}
