package com.mdd.common.entity.apif;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Date;

@Data
@ApiModel("接口管理实体")
public class DataApif implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "目录ID")
    private String contentId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "api编码")
    private String apiCode;


    @ApiModelProperty(value = "是否收费")
    private Integer isFree;

    @ApiModelProperty(value = "价格（元）")
    private BigDecimal price;

    @ApiModelProperty(value = "接口文档")
    private String apiDocument;

    @ApiModelProperty(value = "访问链接")
    private String apiUrl;

    @ApiModelProperty(value = "请求方式")
    private String apiMode;

    @ApiModelProperty(value = "图标")
    private String logo;

    @ApiModelProperty(value = "描述内容")
    private String description;

    @ApiModelProperty(value = "排序")
    private Integer sortNum;

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
    private Long tenantId;

}