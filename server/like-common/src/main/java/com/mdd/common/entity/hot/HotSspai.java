package com.mdd.common.entity.hot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("少数派热搜实体")
public class HotSspai implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "批次ID")
    private String batchId;

    @ApiModelProperty(value = "排名")
    private Integer topNum;

    @ApiModelProperty(value = "封面url")
    private String banner;

    @ApiModelProperty(value = "简介")
    private String summary;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "评论数")
    private Integer commentCount;

    @ApiModelProperty(value = "观看数")
    private Integer viewCount;

    @ApiModelProperty(value = "分类")
    private Integer likeCount;

    @ApiModelProperty(value = "是否免费")
    private String free;

    @ApiModelProperty(value = "发布时间")
    private Date releasedTime;

    @ApiModelProperty(value = "作者昵称")
    private String authorAvatar;

    @ApiModelProperty(value = "评论数")
    private Integer authorNickname;

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