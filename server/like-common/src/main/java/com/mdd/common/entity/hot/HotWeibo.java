package com.mdd.common.entity.hot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("微博热榜实体")
public class HotWeibo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "批次ID")
    private String batchId;

    @ApiModelProperty(value = "排名")
    private Integer topNum;

    @ApiModelProperty(value = "标题")
    private String word;

    @ApiModelProperty(value = "新闻ID")
    private String url;

    @ApiModelProperty(value = "热度值")
    private String num;

    @ApiModelProperty(value = "文章ID")
    private String imageUrl;

    @ApiModelProperty(value = "文章ID")
    private Integer mid;

    @ApiModelProperty(value = "发布时间")
    private Date onboardTime;

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