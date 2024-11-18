package com.mdd.common.vo.ai;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("apikey校验")
public class AiApiKeyVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * apiKey是否正常
     */
    private  boolean flag;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 用户ID
     */
    private String userId;

}
