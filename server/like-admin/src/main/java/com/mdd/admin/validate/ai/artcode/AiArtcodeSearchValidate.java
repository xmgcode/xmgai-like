package com.mdd.admin.validate.ai.artcode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel("二维码生成搜素参数")
public class AiArtcodeSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "预览地址")
    private String urls;

    @ApiModelProperty(value = "持续时间")
    private BigDecimal duration;

    @ApiModelProperty(value = "消耗点数")
    private Long cost;

    @ApiModelProperty(value = "模型编号")
    private Long mode;

    @ApiModelProperty(value = "回调地址")
    private String callbackOriginUrl;

    @ApiModelProperty(value = "状态")
    private Long status;

}
