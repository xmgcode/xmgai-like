package com.mdd.common.entity.ai;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("艺术二维码模板实体")
public class AiArtcodeModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "模板序号")
    private Long modelNum;

    @ApiModelProperty(value = "模板名称")
    private String name;

    @ApiModelProperty(value = "预览地址")
    private String previewImg;

    @ApiModelProperty(value = "是否需要会员")
    private Integer isVip;

    @ApiModelProperty(value = "消耗点数")
    private Integer points;

    @ApiModelProperty(value = "标签")
    private Integer tag;

    @ApiModelProperty(value = "等级")
    private Integer level;

    @ApiModelProperty(value = "默认iw")
    private Double iw;

    @ApiModelProperty(value = "标签名称")
    private String tagName;

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