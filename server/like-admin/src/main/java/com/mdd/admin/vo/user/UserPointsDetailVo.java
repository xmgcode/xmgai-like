package com.mdd.admin.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel("用户点数详情Vo")
public class UserPointsDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "总点数")
    private BigDecimal totalPoints;

    @ApiModelProperty(value = "剩余点数")
    private BigDecimal remainPoints;


}
