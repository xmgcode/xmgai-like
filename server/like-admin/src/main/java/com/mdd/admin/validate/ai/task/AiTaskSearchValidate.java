package com.mdd.admin.validate.ai.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import java.util.Date;
import java.util.Date;

@Data
@ApiModel("任务调度搜素参数")
public class AiTaskSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务名称")
    private String name;

    @ApiModelProperty(value = "任务状态")
    private String status;

    @ApiModelProperty(value = "任务类型")
    private String type;

    @ApiModelProperty(value = "业务类型")
    private String business;

    @ApiModelProperty(value = "任务开始时间")
    private Date startTime;

    @ApiModelProperty(value = "任务结束时间")
    private Date endTime;

    @ApiModelProperty(value = "执行时间（秒）")
    private Integer totalTime;

    @ApiModelProperty(value = "是否删除")
    private Integer deleted;

}
