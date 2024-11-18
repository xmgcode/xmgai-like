package com.mdd.common.entity.apif;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("用户接口实体")
public class DataApifUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "接口ID")
    private String apifId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "总次数")
    private Integer total;

    @ApiModelProperty(value = "剩余次数")
    private Integer remain;

    @ApiModelProperty(value = "接口关键词")
    private String apiKey;

    @ApiModelProperty(value = "接口秘钥")
    private String apiSecret;

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