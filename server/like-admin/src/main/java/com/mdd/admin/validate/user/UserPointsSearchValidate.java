package com.mdd.admin.validate.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("用户点数搜素参数")
public class UserPointsSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "开始时间")
    private String createTimeStart;

    @ApiModelProperty(value = "结束时间")
    private String createTimeEnd;

    @ApiModelProperty(value = "开始时间")
    private String updateTimeStart;

    @ApiModelProperty(value = "结束时间")
    private String updateTimeEnd;

}
