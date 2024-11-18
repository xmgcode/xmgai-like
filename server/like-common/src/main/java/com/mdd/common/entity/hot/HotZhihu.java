package com.mdd.common.entity.hot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("知乎热点实体")
public class HotZhihu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "排名")
    private Integer topNum;

    @ApiModelProperty(value = "批次ID")
    private String batchId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "创作者")
    private String created;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "回答数量")
    private String answerCount;

    @ApiModelProperty(value = "摘录")
    private String excerpt;

    @ApiModelProperty(value = "关注量")
    private String followerCount;

    @ApiModelProperty(value = "问题链接")
    private String url;

    @ApiModelProperty(value = "缩略图")
    private String chumb;

    @ApiModelProperty(value = "热度")
    private String hotLevel;

    @ApiModelProperty(value = "知乎链接")
    private String zhihuUrl;

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