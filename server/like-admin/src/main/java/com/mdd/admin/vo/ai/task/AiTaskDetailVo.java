package com.mdd.admin.vo.ai.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import java.util.Date;
import java.util.Date;

@Data
@ApiModel("任务调度详情Vo")
public class AiTaskDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "任务名称")
    private String name;

    @ApiModelProperty(value = "任务状态")
    private String status;

    @ApiModelProperty(value = "任务类型")
    private String type;

    @ApiModelProperty(value = "业务类型")
    private String business;

    @ApiModelProperty(value = "是否删除")
    private Integer deleted;


}
