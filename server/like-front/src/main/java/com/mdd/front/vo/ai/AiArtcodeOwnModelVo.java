package com.mdd.front.vo.ai;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("艺术二维码模板实体")
public class AiArtcodeOwnModelVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "模板序号")
    private Long modelNum;

    @ApiModelProperty(value = "模板名称")
    private String name;

    @ApiModelProperty(value = "预览地址")
    private String previewImg;



}