package com.mdd.common.vo.apif;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel("接口管理实体")
public class DataApifVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "接口名称")
    private String name;

    @ApiModelProperty(value = "接口名称")
    private String isFree;

    @ApiModelProperty(value = "接口文档")
    private String apiDocument;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "接口ID")
    private String apifId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "总次数")
    private Integer totalNum;

    @ApiModelProperty(value = "剩余次数")
    private Integer remain;

    @ApiModelProperty(value = "接口关键词")
    private String apiKey;

    @ApiModelProperty(value = "接口秘钥")
    private String apiSecret;

    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    @ApiModelProperty(value = "数量")
    private Integer amount;

    @ApiModelProperty(value = "总价")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "支付状态")
    private String payStatus;



}