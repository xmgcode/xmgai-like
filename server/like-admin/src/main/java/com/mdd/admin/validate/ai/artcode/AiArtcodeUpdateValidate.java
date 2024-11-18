package com.mdd.admin.validate.ai.artcode;

import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 二维码生成参数
 * @author xmg
 */
@Data
@ApiModel("二维码生成更新参数")
public class AiArtcodeUpdateValidate implements Serializable {

    private static final Long serialVersionUID = 1L;

    @IDMust(message = "id参数必传")
    @ApiModelProperty(value = "主键")
    private String id;

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

    @ApiModelProperty(value = "状态")
    private Integer status;

}
