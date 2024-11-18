package com.mdd.common.entity.hot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;

@Data
@ApiModel("腾讯热榜实体")
public class HotQq implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "批次ID")
    private String batchId;

    @ApiModelProperty(value = "文章类型")
    private String articletype;

    @ApiModelProperty(value = "新闻ID")
    private String url;

    @ApiModelProperty(value = "排名")
    private Integer topNum;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "长标题")
    private String longtitle;

    @ApiModelProperty(value = "链接")
    private String surl;

    @ApiModelProperty(value = "租户编号")
    private Integer tenantId;

    @ApiModelProperty(value = "长链接")
    private String shortUrl;

    @ApiModelProperty(value = "发布时间")
    private String publishTime;

    @ApiModelProperty(value = "概要")
    private String abstracts;

    @ApiModelProperty(value = "评论数")
    private Integer comments;

    @ApiModelProperty(value = "作者简介")
    private String chlmrk;

    @ApiModelProperty(value = "作者封面")
    private String chlsicon;

    @ApiModelProperty(value = "作者名称")
    private String chlname;

    @ApiModelProperty(value = "封面")
    private String imageUrl;

    @ApiModelProperty(value = "阅读数")
    private Integer readcount;

    @ApiModelProperty(value = "作者地址")
    private String useraddress;

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