package com.mdd.common.entity.hot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("网易热搜实体")
public class MkHot163 implements Serializable {

    private static final long serialVersionUID = 1L;

//    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "排名")
    private Integer topNum;

    @ApiModelProperty(value = "批次ID")
    private String batchId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "发布时间")
    private Date publishTime;

    @ApiModelProperty(value = "封面")
    private String imgsrc;

    @ApiModelProperty(value = "来源")
    private String source;

    @ApiModelProperty(value = "类型")
    private String skipType;

    @ApiModelProperty(value = "新闻ID")
    private String skipId;

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
    private Integer isDelete;

}