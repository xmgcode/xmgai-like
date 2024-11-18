package com.mdd.admin.validate.ai.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.Date;
import java.util.Date;
import java.util.Date;
import com.mdd.common.validator.annotation.IDMust;

/**
 * 任务调度参数
 * @author xmg
 */
@Data
@ApiModel("任务调度更新参数")
public class AiTaskUpdateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @IDMust(message = "id参数必传")
    @ApiModelProperty(value = "主键")
    private String id;

    @NotNull(message = "name参数缺失")
    @ApiModelProperty(value = "任务名称")
    private String name;

    @NotNull(message = "status参数缺失")
    @ApiModelProperty(value = "任务状态")
    private String status;

    @NotNull(message = "type参数缺失")
    @ApiModelProperty(value = "任务类型")
    private String type;

    @NotNull(message = "business参数缺失")
    @ApiModelProperty(value = "业务类型")
    private String business;

    @NotNull(message = "deleted参数缺失")
    @ApiModelProperty(value = "是否删除")
    private Integer deleted;

}
