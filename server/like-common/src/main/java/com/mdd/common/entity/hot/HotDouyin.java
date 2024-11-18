package com.mdd.common.entity.hot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import java.util.Date;

@Data
@ApiModel("抖音热搜实体")
public class HotDouyin implements Serializable {

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

    @ApiModelProperty(value = "更新时间")
    private Date activeTime;

    @ApiModelProperty(value = "分组ID")
    private String groupId;

    @ApiModelProperty(value = "热度")
    private Integer hotValue;

    @ApiModelProperty(value = "排行")
    private Integer position;

    @ApiModelProperty(value = "视频ID")
    private String sentenceId;

    @ApiModelProperty(value = "分类")
    private Integer sentenceTag;

    @ApiModelProperty(value = "访问链接")
    private String url;

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