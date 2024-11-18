package com.mdd.common.entity.apif;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("接口日志实体")
public class DataApifUserLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "接口ID")
    private String apifId;

    @ApiModelProperty(value = "接口关键词")
    private String apiKey;

    @ApiModelProperty(value = "价格（元）")
    private BigDecimal price;

    @ApiModelProperty(value = "请求参数")
    private String requestParams;

    @ApiModelProperty(value = "响应参数")
    private String responseParams;

    @ApiModelProperty(value = "请求ip")
    private String apiIp;

    @ApiModelProperty(value = "响应时间")
    private Date responseTime;

    @ApiModelProperty(value = "响应状态")
    private String responseStatus;

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