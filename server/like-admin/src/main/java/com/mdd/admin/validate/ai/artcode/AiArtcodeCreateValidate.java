package com.mdd.admin.validate.ai.artcode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel("二维码生成创建参数")
public class AiArtcodeCreateValidate implements Serializable {

    private static final Long serialVersionUID = 1L;

    @NotNull(message = "imgUuid参数缺失")
    @ApiModelProperty(value = "图片ID")
    private String imgUuid;

    @NotNull(message = "userId参数缺失")
    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "预览地址")
    private String urls;

    @ApiModelProperty(value = "本地地址")
    private String localUrls;

    @ApiModelProperty(value = "持续时间")
    private BigDecimal duration;

    @ApiModelProperty(value = "消耗点数")
    private Integer cost;

    @ApiModelProperty(value = "模型编号")
    private Integer mode;

    @ApiModelProperty(value = "回调地址")
    private String callbackOriginUrl;

}