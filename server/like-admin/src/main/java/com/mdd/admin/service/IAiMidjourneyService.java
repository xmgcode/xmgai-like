package com.mdd.admin.service;

import com.mdd.admin.validate.ai.mj.AiMjCreateValidate;
import com.mdd.admin.validate.ai.mj.AiMjFaceSwapValidate;

/**
 * 二维码生成服务接口类
 * @author xmg
 */
public interface IAiMidjourneyService {

    /**
     * @Author xmg
     * 提交AI绘画任务
     * Date 2023/5/19 10:09
     */
    String imagine(AiMjCreateValidate aiMjCreateValidate);


    /**
     * MJ换脸
     * @param aiMjFaceSwapValidate
     * @return
     */
    String faceSwap(AiMjFaceSwapValidate aiMjFaceSwapValidate);



}
