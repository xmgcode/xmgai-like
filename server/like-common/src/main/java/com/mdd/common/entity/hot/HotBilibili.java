package com.mdd.common.entity.hot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("哔哩热榜实体")
public class HotBilibili implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "批次ID")
    private String batchId;

    @ApiModelProperty(value = "排名")
    private int topNum;

    @ApiModelProperty(value = "aid")
    private String aid;

    @ApiModelProperty(value = "视频类型")
    private String videos;

    @ApiModelProperty(value = "分类名称")
    private String tname;

    @ApiModelProperty(value = "封面图片")
    private String pic;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "发布时间")
    private Date pubdate;

    @ApiModelProperty(value = "ctime")
    private Date ctime;

    @ApiModelProperty(value = "描述")
    private String descp;

    @ApiModelProperty(value = "作者ID")
    private String mid;

    @ApiModelProperty(value = "作者名称")
    private String name;

    @ApiModelProperty(value = "作者封面")
    private String face;

    @ApiModelProperty(value = "观看量")
    private Integer views;

    @ApiModelProperty(value = "弹幕量")
    private Integer danmaku;

    @ApiModelProperty(value = "回复量")
    private Integer reply;

    @ApiModelProperty(value = "收藏量")
    private Integer favorite;

    @ApiModelProperty(value = "投币量")
    private Integer coin;

    @ApiModelProperty(value = "转发量")
    private Integer shares;

    @ApiModelProperty(value = "点赞量")
    private Integer likes;

    @ApiModelProperty(value = "不喜欢量")
    private Integer dislike;

    @ApiModelProperty(value = "视频链接")
    private String shortLinkV2;

    @ApiModelProperty(value = "第一帧")
    private String firstFrame;

    @ApiModelProperty(value = "发布位置")
    private String pubLocation;

    @ApiModelProperty(value = "视频ID")
    private String bvid;

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