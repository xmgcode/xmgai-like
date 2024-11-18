package com.mdd.common.entity.ai;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("二维码生成实体")
public class AiArtcode implements Serializable {

    private static final Long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "图片ID")
    private String imgUuid;

    @ApiModelProperty(value = "AI类型")
    private String aiType;

    @ApiModelProperty(value = "AI名称")
    private String aiName;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "提示词")
    private String prompt;

    @ApiModelProperty(value = "优化后的提示词")
    private String revisedPrompt;

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

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updater;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除")
    private Integer deleted;

}