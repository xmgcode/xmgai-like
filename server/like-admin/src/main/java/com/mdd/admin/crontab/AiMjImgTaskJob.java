package com.mdd.admin.crontab;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdd.common.cache.ConfigCache;
import com.mdd.common.entity.ai.AiArtcode;
import com.mdd.common.entity.ai.AiTask;
import com.mdd.common.enums.AiEnum;
import com.mdd.common.enums.AlbumEnum;
import com.mdd.common.mapper.ai.AiArtcodeMapper;
import com.mdd.common.mapper.ai.AiTaskMapper;
import com.mdd.common.plugin.storage.StorageDriver;
import com.mdd.common.plugin.storage.UploadFilesVo;
import com.mdd.common.util.HttpClientUtil;
import com.mdd.common.util.SnowIdUtil;
import com.mdd.common.util.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 具体的定时任务
 */
@Component("mjImgJob")
@Slf4j
public class AiMjImgTaskJob {

    @Resource
    private AiTaskMapper aiTaskMapper;

    @Resource
    private AiArtcodeMapper aiArtcodeMapper;


    public void handle() {
        // 查询所有状态为0的任务
        List<AiTask> taskList = aiTaskMapper.selectList(new QueryWrapper<AiTask>()
                .eq("status", 0)
                .eq("type", AiEnum.MjImagine.getValue())
                .orderByAsc("create_time"));
        log.info("当前文生图任务数：" + taskList.size());
        for(int i=0;i<taskList.size();i++){
            AiTask task = taskList.get(i);
            // 实际的任务执行逻辑
            task.setStartTime(new Date());
            //从缓存中获取配置
            Map<String, Object> configMap = ConfigCache.getAiConfigCache();
            String configStr = configMap.get("midjourney").toString();
            JSONObject obj = JSONObject.parseObject(configStr);
            String aiMjTaskUrl = obj.get("aimjtask").toString();
            // 获取并解析响应体为字符串

            String taskId = task.getBusiness();

            HashMap dataMap = new HashMap();
            dataMap.put("taskId",taskId);
            String res = HttpClientUtil.doGetParams(aiMjTaskUrl,null,dataMap,3000);
            JSONObject object = JSONObject.parseObject(res);
            if(null != object && "200".equals(object.get("code").toString())){
                JSONObject resObj = (JSONObject)object.get("data");
                if("100%".equals(resObj.get("progress").toString())){

                    String urlStr =  resObj.get("imageUrl").toString();
                    log.info("生成的MJ生图访问路径为：" + urlStr);
                    //保存MJ文生图到本地
                    UploadFilesVo vo = new UploadFilesVo();
                    try {
                        MultipartFile multipartFile = ToolUtils.convertUrlToMultipartFile(urlStr);
                        StorageDriver storageDriver = new StorageDriver();
                        vo = storageDriver.upload(multipartFile, "image", AlbumEnum.IMAGE.getCode());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    AiArtcode aiArtcode = new AiArtcode();
                    aiArtcode.setId(SnowIdUtil.getStrUUid());
                    aiArtcode.setImgUuid(task.getBusiness());
                    aiArtcode.setUserId(task.getUserId());
                    aiArtcode.setUrls(resObj.get("imageUrl").toString());
                    aiArtcode.setAiName(AiEnum.MjImagine.getName());
                    aiArtcode.setAiType(AiEnum.MjImagine.getValue());
                    aiArtcode.setCallbackOriginUrl("http://www.baidu.com");
                    aiArtcode.setLocalUrls(vo.getUrl());
                    aiArtcode.setStatus(1);
                    aiArtcode.setCreateTime(new Date());
                    aiArtcodeMapper.insert(aiArtcode);
                    log.info("任务调度：MJ文生图生成成功!");
                    //更新任务调度状态
                    task.setStatus("1");
                    task.setEndTime(new Date());
                    task.setTotalTime(calculateDifferenceInSeconds(task.getEndTime() , task.getStartTime()));
                    aiTaskMapper.updateById(task);
                    log.info("文生图任务查询成功！taskId: " + taskId);
                }

            }else {
                log.info("换脸任务查询失败！taskId: " + taskId);
            }

        }
    }


    public long calculateDifferenceInSeconds(Date date1, Date date2) {
        // 将Date对象转换为时间戳
        long time1 = date1.getTime();
        long time2 = date2.getTime();

        // 计算差值
        long difference = Math.abs(time2 - time1);

        // 将毫秒转换为秒
        return difference / 1000;
    }
}
