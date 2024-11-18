package com.mdd.admin.validate.ai.model;

import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 艺术二维码模板参数
 * @author xmg
 */
@Data
@ApiModel("艺术二维码模板更新参数")
public class AiArtcodeModelUpdateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @IDMust(message = "id参数必传")
    @ApiModelProperty(value = "主键")
    private String id;

    @NotNull(message = "modelNum参数缺失")
    @ApiModelProperty(value = "模板序号")
    private Long modelNum;

    @NotNull(message = "name参数缺失")
    @ApiModelProperty(value = "模板名称")
    private String name;

    @NotNull(message = "previewImg参数缺失")
    @ApiModelProperty(value = "预览地址")
    private String previewImg;

}
