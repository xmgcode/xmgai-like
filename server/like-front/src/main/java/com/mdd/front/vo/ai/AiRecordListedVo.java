package com.mdd.front.vo.ai;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("二维码生成列表Vo")
public class AiRecordListedVo implements Serializable {

    private static final Long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "图片ID")
    private String imgUuid;


    @ApiModelProperty(value = "预览地址")
    private String urls;

    @ApiModelProperty(value = "本地地址")
    private String localUrls;

    @ApiModelProperty(value = "提示词")
    private String prompt;

    @ApiModelProperty(value = "优化后的提示词")
    private String revisedPrompt;


    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
