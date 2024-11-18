package com.mdd.common.entity.hot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("澎湃热榜实体")
public class HotPaper implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "批次ID")
    private String batchId;

    @ApiModelProperty(value = "排名")
    private Integer topNum;

    @ApiModelProperty(value = "新闻ID")
    private String contId;

    @ApiModelProperty(value = "互动数")
    private Integer interactionNum;

    @ApiModelProperty(value = "点赞数")
    private Integer praisTimes;

    @ApiModelProperty(value = "封面图片")
    private String pic;

    @ApiModelProperty(value = "新闻标题")
    private String name;

    @ApiModelProperty(value = "访问链接")
    private String url;

    @ApiModelProperty(value = "发布时间")
    private String pubTime;

    @ApiModelProperty(value = "作者姓名")
    private String authName;

    @ApiModelProperty(value = "作者简介")
    private String summarize;

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

    @ApiModelProperty(value = "租户编号")
    private Integer tenantId;

}