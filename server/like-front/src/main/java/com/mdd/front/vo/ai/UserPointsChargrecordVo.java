package com.mdd.front.vo.ai;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("充值记录实体")
public class UserPointsChargrecordVo implements Serializable {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty(value = "充值金额")
    private BigDecimal money;

    @ApiModelProperty(value = "兑换点数")
    private BigDecimal obtainPoints;

    @ApiModelProperty(value = "赠送点数")
    private BigDecimal givePoints;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}