package com.mdd.admin.validate.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.Date;

@Data
@ApiModel("使用记录搜素参数")
public class UserPointsUserecordSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "使用场景")
    private String scene;

    @ApiModelProperty(value = "开始时间")
    private String createTimeStart;

    @ApiModelProperty(value = "结束时间")
    private String createTimeEnd;

}
