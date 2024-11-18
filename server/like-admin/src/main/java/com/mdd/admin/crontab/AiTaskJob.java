package com.mdd.admin.crontab;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdd.common.entity.ai.AiArtcode;
import com.mdd.common.entity.ai.AiTask;
import com.mdd.common.enums.AiEnum;
import com.mdd.common.mapper.ai.AiArtcodeMapper;
import com.mdd.common.mapper.ai.AiTaskMapper;
import com.mdd.common.util.SnowIdUtil;
import com.mdd.common.util.YmlUtils;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * 具体的定时任务
 */
@Component("aiTaskJob")
@Slf4j
public class AiTaskJob {

    @Resource
    private AiTaskMapper aiTaskMapper;

    @Resource
    private AiArtcodeMapper aiArtcodeMapper;


    public void handle() {
        // 查询所有状态为0的任务
        List<AiTask> taskList = aiTaskMapper.selectList(new QueryWrapper<AiTask>()
                .eq("status", 0)
                .eq("type", AiEnum.AiArtCode.getValue()));
        log.info("当前艺术二维码任务数：" + taskList.size());
        for(int i=0;i<taskList.size();i++){
            AiTask task = taskList.get(i);
            // 实际的任务执行逻辑
            task.setStartTime(new Date());
            // 执行具体的任务逻辑，例如调用外部接口等
            JSONObject resQr = new JSONObject();
            String imageUrl = YmlUtils.get("xmg.api.imageUrl");
            String qrImageUrl = YmlUtils.get("xmg.api.qrImageUrl");
            String apiKey = YmlUtils.get("xmg.api.apikey");
            //获取二维码
            String pjUrl = qrImageUrl + "?imageId=" + task.getBusiness();
            HttpResponse<String> requestQr = Unirest.get(pjUrl)
                    .header("apiKey", apiKey)
                    .header("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                    .asString();
            // 获取并解析响应体为字符串
            String getQrStr = requestQr.getBody().toString();
            //todo 以上内容待释放
//            String getQrStr = "{\"code\":0,\"msg\":\"成功\",\"data\":{\"img_uuid\":\"b19e2828-c5fb-43c8-8e01-c1bd14b74b0c\",\"user_id\":\"20240619082927ff01e1\",\"urls\":[\"http://image.miliit.com/blog/admin/jpg/2024/2/1/1706759477309.jpg\"],\"duration\":171.61,\"cost\":1,\"mode\":1,\"callback_origin_url\":\"https://www.baidu.com\",\"status\":1}}";
            JSONObject imageObj = JSON.parseObject(getQrStr);
            String code = imageObj.get("code").toString();
            if("200".equals(code)){
                String qrcodeUrl = imageObj.get("data").toString();
                resQr.put("code","1");
                resQr.put("msg","二维码生成成功！");
                log.info("生成的二维码访问路径为：" + qrcodeUrl);
                //保存二维码实体
                AiArtcode aiArtcode = new AiArtcode();
                aiArtcode.setId(SnowIdUtil.getStrUUid());
                aiArtcode.setImgUuid(task.getBusiness());
                aiArtcode.setUserId(task.getUserId());
                aiArtcode.setUrls(imageUrl + qrcodeUrl);
                aiArtcode.setAiName(AiEnum.AiArtCode.getName());
                aiArtcode.setAiType(AiEnum.AiArtCode.getValue());
                aiArtcode.setCallbackOriginUrl("http://www.baidu.com");
                aiArtcode.setLocalUrls(imageUrl + qrcodeUrl);
                aiArtcode.setStatus(1);
                aiArtcode.setCreateTime(new Date());
                aiArtcodeMapper.insert(aiArtcode);
                log.info("任务调度：AI二维码生成成功!");
                //更新任务调度状态
                task.setStatus("1");
                task.setEndTime(new Date());
                task.setTotalTime(calculateDifferenceInSeconds(task.getEndTime() , task.getStartTime()));
                aiTaskMapper.updateById(task);
            }
            // 示例中只是简单地打印任务信息，您需要根据实际需求修改
            System.out.println("Task " + task.getId() + " completed.");

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
