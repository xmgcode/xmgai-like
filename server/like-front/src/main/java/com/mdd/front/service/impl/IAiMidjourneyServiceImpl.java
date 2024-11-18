package com.mdd.front.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdd.admin.validate.ai.mj.AiMjCreateValidate;
import com.mdd.common.cache.ConfigCache;
import com.mdd.common.util.HttpClientUtil;
import com.mdd.common.util.StringUtils;
import com.mdd.front.service.IAiMidjourneyService;
import com.mdd.front.validate.ai.mj.AiDalle3CreateValidate;
import com.mdd.front.validate.ai.mj.AiMjFaceSwapValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * MJ相关接口
 * @author xmg
 * @date 2023/4/7
 */
@Slf4j
@Service
public class IAiMidjourneyServiceImpl implements IAiMidjourneyService {

    @Override
    public String imagine(AiMjCreateValidate aiMjCreateValidate) {
        //从缓存中获取配置
        Map<String, Object> configMap = ConfigCache.getAiConfigCache();
        String configStr = configMap.get("midjourney").toString();
        JSONObject obj = JSONObject.parseObject(configStr);
        String imagineUrl = obj.get("imagine").toString();
        //获取APIkey
        String dataConfigStr = configMap.get("dataguan").toString();
        JSONObject dataObj = JSONObject.parseObject(dataConfigStr);
        String Authorization = dataObj.get("apikey").toString();
        HashMap headMap = new HashMap();
        HashMap dataMap = new HashMap();
        headMap.put("apiKey",Authorization);
        headMap.put("Content-Type","application/json;charset=utf-8");
        dataMap.put("mode", aiMjCreateValidate.getMode());
        dataMap.put("prompt", aiMjCreateValidate.getPrompt());
        String[] args = aiMjCreateValidate.getBase64Array();
        if(!StringUtils.isEmpty(args) && args.length>0){
            dataMap.put("base64Array", aiMjCreateValidate.getPrompt());
        }
        int timeout = 120000;
        // 填充map数据
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(dataMap);
            String res = HttpClientUtil.doPost(imagineUrl,headMap,jsonString,timeout);
            return res;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String faceSwap(AiMjFaceSwapValidate aiMjFaceSwapValidate) {
        //从缓存中获取配置
        Map<String, Object> configMap = ConfigCache.getAiConfigCache();
        String configStr = configMap.get("midjourney").toString();
        JSONObject obj = JSONObject.parseObject(configStr);
        String imagineUrl = obj.get("faceswap").toString();
        //获取APIkey
        String dataConfigStr = configMap.get("dataguan").toString();
        JSONObject dataObj = JSONObject.parseObject(dataConfigStr);
        String Authorization = dataObj.get("apikey").toString();
        log.info("获取MJ换脸地址为：" + imagineUrl);
        HashMap headMap = new HashMap();
        HashMap dataMap = new HashMap();
        headMap.put("apiKey",Authorization);
        headMap.put("Content-Type","application/json;charset=utf-8");
        dataMap.put("mode", aiMjFaceSwapValidate.getMode());
        dataMap.put("sourceBase64",aiMjFaceSwapValidate.getSourceBase64());
        dataMap.put("targetBase64",aiMjFaceSwapValidate.getTargetBase64());
        int timeout = 120000;
        // 填充map数据
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(dataMap);
            String res = HttpClientUtil.doPost(imagineUrl,headMap,jsonString,timeout);
            return res;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String dalle3(AiDalle3CreateValidate aiDalle3CreateValidate) {
        //从缓存中获取配置
        Map<String, Object> configMap = ConfigCache.getAiConfigCache();
        String configStr = configMap.get("chatgpt").toString();
        JSONObject obj = JSONObject.parseObject(configStr);
        String imagineUrl = obj.get("dalle3").toString();

        //获取APIkey
        String dataConfigStr = configMap.get("dataguan").toString();
        JSONObject dataObj = JSONObject.parseObject(dataConfigStr);
        String Authorization = dataObj.get("apikey").toString();
        HashMap headMap = new HashMap();
        HashMap dataMap = new HashMap();
        headMap.put("apiKey",Authorization);
        headMap.put("Content-Type","application/json;charset=utf-8");
        dataMap.put("size",aiDalle3CreateValidate.getSize());
        dataMap.put("prompt",aiDalle3CreateValidate.getPrompt());
        int timeout = 120000;
        // 填充map数据
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(dataMap);
            String res = HttpClientUtil.doPost(imagineUrl,headMap,jsonString,timeout);
            return res;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

