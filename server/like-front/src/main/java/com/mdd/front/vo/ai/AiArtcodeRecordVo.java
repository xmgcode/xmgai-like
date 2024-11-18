package com.mdd.front.vo.ai;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("二维码生成实体")
public class AiArtcodeRecordVo implements Serializable {

    private static final Long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "本地地址")
    private String localUrls;

    @ApiModelProperty(value = "提示词")
    private String prompt;

    @ApiModelProperty(value = "优化后的提示词")
    private String revisedPrompt;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}