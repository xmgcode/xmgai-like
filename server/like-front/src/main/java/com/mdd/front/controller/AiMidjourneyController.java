package com.mdd.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.mdd.admin.validate.ai.mj.AiMjCreateValidate;
import com.mdd.common.aop.NotLogin;
import com.mdd.common.cache.ConfigCache;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.entity.ai.AiArtcode;
import com.mdd.common.entity.ai.AiTask;
import com.mdd.common.enums.AiEnum;
import com.mdd.common.enums.AlbumEnum;
import com.mdd.common.enums.ErrorEnum;
import com.mdd.common.mapper.ai.AiArtcodeMapper;
import com.mdd.common.mapper.ai.AiTaskMapper;
import com.mdd.common.plugin.storage.StorageDriver;
import com.mdd.common.plugin.storage.UploadFilesVo;
import com.mdd.common.util.*;
import com.mdd.common.vo.ai.AiApiKeyVo;
import com.mdd.front.LikeFrontThreadLocal;
import com.mdd.front.service.IAiMidjourneyService;
import com.mdd.front.validate.ai.mj.AiDalle3CreateValidate;
import com.mdd.front.validate.ai.mj.AiMjFaceSwapValidate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/mj")
@Api(tags = "mj绘画管理")
@Slf4j
public class AiMidjourneyController {

    @Resource
    IAiMidjourneyService iAiMidjourneyService;


    @Autowired
    private ApiKeyPointUtils apiKeyPointUtils;

    @Autowired
    private AiArtcodeMapper aiArtcodeMapper;

    @Autowired
    private AiTaskMapper aiTaskMapper;




    @NotLogin
    @PostMapping("/imagine")
    @ApiOperation(value="AI配置编辑")
    public AjaxResult<Object> imagine(@Validated @RequestBody AiMjCreateValidate aiMjCreateValidate, HttpServletRequest request) {
        String userId = String.valueOf(LikeFrontThreadLocal.getUserId());
        userId = "20000017";
        if(com.mdd.common.util.StringUtils.isEmpty(userId)){
            return AjaxResult.failed("获取用户ID失败！");
        }

        //检验积分
        AiApiKeyVo aiApiKeyVo = apiKeyPointUtils.validateApiKey(userId,"points","imagine-points");
        boolean flag = aiApiKeyVo.isFlag();
        if(!flag){
            return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),aiApiKeyVo.getMsg());
        }else {
            //开始请求AI换脸任务
            aiMjCreateValidate.setMode("RELAX");
//            String res = iAiMidjourneyService.imagine(aiMjCreateValidate);
            String res = "{\"code\":200,\"msg\":\"提交成功\",\"data\":\"1723526552063854\"}";
            log.info("MJ文生图结果返回为：" + res);
            JSONObject jsonObject = JSONObject.parseObject(res);
            //扣除积分、添加任务
            AjaxResult<Object> resObj =  mjImgResult(AiEnum.MjImagine,userId,"points","imagine-points",jsonObject);
            return resObj;

        }

    }


    /**
     * @Author xmg
     * MJ换脸
     * @param
     * @return
     */
    @PostMapping("/faceswap")
    @ApiOperation(value="AI换脸")
    public AjaxResult<Object> faceswap(@RequestPart("originFile") MultipartFile originFile,@RequestPart("targetFile") MultipartFile targetFile) {
        if (!originFile.isEmpty() && !targetFile.isEmpty()) {
            try {
                String userId = String.valueOf(LikeFrontThreadLocal.getUserId());
                if(com.mdd.common.util.StringUtils.isEmpty(userId)){
                    return AjaxResult.failed("获取用户ID失败！");
                }
                String originBase64 = Base64Util.imgToBase64(originFile);
                String targetBase64 = Base64Util.imgToBase64(targetFile);
                //检验积分
                AiApiKeyVo aiApiKeyVo = apiKeyPointUtils.validateApiKey(userId,"points","faceswap-points");
                boolean flag = aiApiKeyVo.isFlag();
                if(!flag){
                    return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),aiApiKeyVo.getMsg());
                }else {
                    //开始请求AI换脸任务
                    AiMjFaceSwapValidate aiMjFaceSwapValidate = new AiMjFaceSwapValidate();
                    aiMjFaceSwapValidate.setMode("RELAX");
                    aiMjFaceSwapValidate.setSourceBase64(originBase64);
                    aiMjFaceSwapValidate.setTargetBase64(targetBase64);
                    String res = iAiMidjourneyService.faceSwap(aiMjFaceSwapValidate);
//                    String res = "{\"code\":200,\"msg\":\"提交成功\",\"data\":\"1722096033599127\"}";
                    log.info("换脸结果返回为：" + res);
                    JSONObject jsonObject = JSONObject.parseObject(res);
                    //扣除积分、添加任务
                    AjaxResult<Object> resObj =  mjFaceResult(AiEnum.MjFaceswap,userId,"points","faceswap-points",jsonObject);
                    return resObj;

                }

            }catch (Exception e) {
                e.printStackTrace();
                return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),"图片获取异常！");
            }
        }else{
            return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),"图片获取失败！");
        }

    }





    /**
     * @Author xmg
     * MJ换脸
     * @param
     * @return
     */
    @PostMapping("/dall")
    @ApiOperation(value="dall绘画")
    public AjaxResult<Object> dall(@Validated @RequestBody AiDalle3CreateValidate aiDalle3CreateValidate, HttpServletRequest request) {
        if(StringUtils.isBlank(aiDalle3CreateValidate.getSize())){
            return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),"绘画尺寸不能为空！");
        }
        String userId = String.valueOf(LikeFrontThreadLocal.getUserId());
        if(com.mdd.common.util.StringUtils.isEmpty(userId)){
            return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),"获取用户失败！");
        }
        //检验积分
        AiApiKeyVo aiApiKeyVo = apiKeyPointUtils.validateApiKey(userId,"points","dalle3-points");
        boolean flag = aiApiKeyVo.isFlag();
        if(!flag){
            return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),aiApiKeyVo.getMsg());
        }else {
            String res = iAiMidjourneyService.dalle3(aiDalle3CreateValidate);
//            String res = "{\"code\":200,\"msg\":\"请求成功!\",\"data\":{\"revisedPrompt\":\"A vast and breathtaking Chinese landscape painting in traditional style. The scene includes expansive rivers, majestic mountains, and dense forests. The sky is filled with gentle, swirling snowflakes that create a serene and peaceful atmosphere. The overall color scheme features deep blues and whites, capturing the essence of both the thousand-mile rivers and the snowy mountains. Traditional Chinese architecture can be seen nestled within the landscape, blending harmoniously with nature.\",\"prompt\":\"中国千里江山、万里雪飘\",\"localUrl\":\"image/20240804/f9536384-74c3-4e0a-98cd-617be8d7079d.png\"}}";
            log.info("绘画结果返回为：" + res);
            JSONObject jsonObject = JSONObject.parseObject(res);
            //扣除积分、添加任务
            AjaxResult<Object> resObj =  dallResult(aiDalle3CreateValidate.getPrompt() ,AiEnum.GptDalle3,aiApiKeyVo.getUserId(),"points","dalle3-points",jsonObject);
            return resObj;
        }
    }



    @NotLogin
    @GetMapping("/queryTask")
    @ApiOperation(value="查询绘画任务")
    public AjaxResult<Object> queryTask(@RequestParam("taskId") String taskId) {
        //从缓存中获取配置
        Map<String, Object> configMap = ConfigCache.getAiConfigCache();
        String configStr = configMap.get("midjourney").toString();
        JSONObject obj = JSONObject.parseObject(configStr);
        String aiMjTaskUrl = obj.get("aimjtask").toString();
        // 获取并解析响应体为字符串
        HashMap dataMap = new HashMap();
        dataMap.put("taskId",taskId);
        String res = HttpClientUtil.doGetParams(aiMjTaskUrl,null,dataMap,3000);
        JSONObject object = JSONObject.parseObject(res);
        if(null != object && "200".equals(object.get("code").toString())){
            Object resObj = object.get("data");
            return AjaxResult.success(ErrorEnum.XMGSUCCESS.getCode(),"任务查询失败！",resObj);
        }else {
            return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),"任务查询失败！");
        }
    }


    /**
     *
     * @param aiEnum 使用场景枚举
     * @param userId 用户id
     * @param aiConfigType la_ai_config 配置中的type
     * @param aiConfigName la_ai_config 配置中的name
     * @param jsonObject
     * @return
     */
    public AjaxResult<Object> dallResult(String prompt,AiEnum aiEnum,String userId,String aiConfigType,String aiConfigName, JSONObject jsonObject){
        if(null != jsonObject && "200".equals(jsonObject.get("code").toString())){
            String data = jsonObject.get("data").toString();
            JSONObject obj = JSONObject.parseObject(data);
            String newPrompt = obj.get("prompt").toString();
            String remoteUrl = obj.get("localUrl").toString();
            String revisedPrompt = obj.get("revisedPrompt").toString();
            //扣除积分
            apiKeyPointUtils.lessPoints(userId,aiEnum.getValue(),aiConfigType,aiConfigName);

            //获取AI绘画访问地址
            String imgPrefix = RedisUtils.getConfigValue("midjourney","img-prefix");
            String resUrl = imgPrefix + remoteUrl;
            log.info("AI图片的访问地址为：" + resUrl);
            //保存AI图片到本地
            UploadFilesVo vo = new UploadFilesVo();
            try {
                MultipartFile multipartFile = ToolUtils.convertUrlToMultipartFile(resUrl);
                StorageDriver storageDriver = new StorageDriver();
                vo = storageDriver.upload(multipartFile, "image", AlbumEnum.IMAGE.getCode());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            //保存实体
            AiArtcode aiArtcode = new AiArtcode();
            aiArtcode.setId(SnowIdUtil.getStrUUid());
            aiArtcode.setUserId(userId);
            aiArtcode.setUrls(vo.getUrl());
            aiArtcode.setPrompt(prompt);
            aiArtcode.setRevisedPrompt(revisedPrompt);
            aiArtcode.setAiName(AiEnum.GptDalle3.getName());
            aiArtcode.setAiType(AiEnum.GptDalle3.getValue());
            aiArtcode.setLocalUrls(vo.getUrl());
            aiArtcode.setStatus(1);
            aiArtcode.setCreateTime(new Date());
            aiArtcodeMapper.insert(aiArtcode);
            return AjaxResult.success(ErrorEnum.XMGSUCCESS.getCode(),"绘画成功!",vo.getUrl());
        }
        return AjaxResult.success(ErrorEnum.XMGFAILED.getCode(),"绘画失败!");

    }




    /**
     *
     * @param aiEnum 使用场景枚举
     * @param userId 用户id
     * @param aiConfigType la_ai_config 配置中的type
     * @param aiConfigName la_ai_config 配置中的name
     * @param jsonObject
     * @return
     */
    public AjaxResult<Object> mjFaceResult(AiEnum aiEnum,String userId,String aiConfigType,String aiConfigName, JSONObject jsonObject){
        if(null != jsonObject && "200".equals(jsonObject.get("code").toString())){
            //扣除积分
            String taskid = jsonObject.get("data").toString();
            String msg = jsonObject.get("msg").toString();
            apiKeyPointUtils.lessPoints(userId,aiEnum.getValue(),aiConfigType,aiConfigName);
            log.info("提交MJ绘画任务成功，绘画ID为：" + taskid);
            //任务调度开始
            AiTask aiTask = new AiTask();
            aiTask.setId(SnowIdUtil.getStrUUid());
            aiTask.setUserId(userId);
            aiTask.setName(aiEnum.getName());
            aiTask.setType(aiEnum.getValue());
            aiTask.setStatus("0");
            aiTask.setCreateTime(new Date());
            aiTask.setBusiness(taskid);
            aiTaskMapper.insert(aiTask);
            return AjaxResult.success(ErrorEnum.XMGSUCCESS.getCode(),msg,taskid);
        }else{
            String msg = jsonObject.get("msg").toString();
            return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),msg);
        }


    }


    /**
     *
     * @param aiEnum 使用场景枚举
     * @param userId 用户id
     * @param aiConfigType la_ai_config 配置中的type
     * @param aiConfigName la_ai_config 配置中的name
     * @param jsonObject
     * @return
     */
    public AjaxResult<Object> mjResult(AiEnum aiEnum,String userId,String aiConfigType,String aiConfigName, JSONObject jsonObject){
        if(null != jsonObject && "200".equals(jsonObject.get("code").toString())){
            //扣除积分
            String taskid = jsonObject.get("data").toString();
            String msg = jsonObject.get("msg").toString();
            apiKeyPointUtils.lessPoints(userId,aiEnum.getValue(),aiConfigType,aiConfigName);
            log.info("提交MJ绘画任务成功，绘画ID为：" + taskid);
            //任务调度开始
            AiTask aiTask = new AiTask();
            aiTask.setId(SnowIdUtil.getStrUUid());
            aiTask.setUserId(userId);
            aiTask.setName(aiEnum.getName());
            aiTask.setType(aiEnum.getValue());
            aiTask.setStatus("0");
            aiTask.setCreateTime(new Date());
            aiTask.setBusiness(taskid);
            aiTaskMapper.insert(aiTask);
            return AjaxResult.success(ErrorEnum.XMGSUCCESS.getCode(),msg,taskid);
        }else{
            String msg = jsonObject.get("msg").toString();
            return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),msg);
        }


    }


    /**
     *
     * @param aiEnum 使用场景枚举
     * @param userId 用户id
     * @param aiConfigType la_ai_config 配置中的type
     * @param aiConfigName la_ai_config 配置中的name
     * @param jsonObject
     * @return
     */
    public AjaxResult<Object> mjImgResult(AiEnum aiEnum,String userId,String aiConfigType,String aiConfigName, JSONObject jsonObject){
        if(null != jsonObject && "200".equals(jsonObject.get("code").toString())){
            //扣除积分
            String taskid = jsonObject.get("data").toString();
            String msg = jsonObject.get("msg").toString();
            apiKeyPointUtils.lessPoints(userId,aiEnum.getValue(),aiConfigType,aiConfigName);
            log.info("提交MJ文生图任务成功，绘画ID为：" + taskid);
            //任务调度开始
            AiTask aiTask = new AiTask();
            aiTask.setId(SnowIdUtil.getStrUUid());
            aiTask.setUserId(userId);
            aiTask.setName(aiEnum.getName());
            aiTask.setType(aiEnum.getValue());
            aiTask.setStatus("0");
            aiTask.setCreateTime(new Date());
            aiTask.setBusiness(taskid);
            aiTaskMapper.insert(aiTask);
            return AjaxResult.success(ErrorEnum.XMGSUCCESS.getCode(),msg,taskid);
        }else{
            String msg = jsonObject.get("msg").toString();
            return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),msg);
        }


    }







}
