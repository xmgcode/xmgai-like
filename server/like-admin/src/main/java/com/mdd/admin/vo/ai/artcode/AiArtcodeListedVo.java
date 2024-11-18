package com.mdd.admin.vo.ai.artcode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("二维码生成列表Vo")
public class AiArtcodeListedVo implements Serializable {

    private static final Long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "图片ID")
    private String imgUuid;

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

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
