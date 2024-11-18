package com.mdd.front.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdd.common.aop.NotLogin;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.ai.AiTask;
import com.mdd.common.entity.user.UserPoints;
import com.mdd.common.entity.user.UserPointsUserecord;
import com.mdd.common.enums.AiEnum;
import com.mdd.common.enums.ErrorEnum;
import com.mdd.common.mapper.ai.AiTaskMapper;
import com.mdd.common.mapper.user.UserPointsMapper;
import com.mdd.common.mapper.user.UserPointsUserecordMapper;
import com.mdd.common.util.HttpClientUtil;
import com.mdd.common.util.SnowIdUtil;
import com.mdd.common.util.StringUtils;
import com.mdd.common.util.YmlUtils;
import com.mdd.front.LikeFrontThreadLocal;
import com.mdd.front.service.IAiArtService;
import com.mdd.front.validate.ai.artcode.AiQrModelCreateValidate;
import com.mdd.front.validate.common.PageValidate;
import com.mdd.front.vo.ai.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/aiart")
@Api(tags = "AI控制层")
@Slf4j
public class AiArtController {

    @Resource
    private UserPointsUserecordMapper userPointsUserecordMapper;

    @Resource
    private UserPointsMapper userPointsMapper;

    @Resource
    private AiTaskMapper aiTaskMapper;


    @Resource
    private IAiArtService iAiArtService;




    @NotLogin
    @GetMapping("/getAllModel")
    @ApiOperation(value="艺术二维码模板列表")
    public AjaxResult<Object> getAllModel() {
        String artcodeModel = YmlUtils.get("xmg.api.artcodeModel");
        String apikey = YmlUtils.get("xmg.api.apikey");
        HashMap headMap = new HashMap();
        headMap.put("apiKey",apikey);
        String res = HttpClientUtil.doGetParams(artcodeModel,headMap,null,3000);
        JSONObject json = JSONObject.parseObject(res);
        Integer code = Integer.valueOf(json.get("code").toString());
        if(200 == code){
            JSONArray data = (JSONArray) json.get("data");
            return AjaxResult.success("成功！",data);
        }
        return AjaxResult.failed("失败！");
    }


    @PostMapping("/createArtcode")
    @ApiOperation(value="艺术二维码生成")
    public AjaxResult<Object> createArtcode(@Validated @RequestBody AiQrModelCreateValidate createCodeValidate, HttpServletRequest request) {
        String userId = String.valueOf(LikeFrontThreadLocal.getUserId());
        if(StringUtils.isEmpty(userId)){
            return AjaxResult.failed("获取当前登录者ID失败！");
        }
        String resEachPoints = YmlUtils.get("xmg.api.eachPoints");
        JSONObject josn = new JSONObject();
        //每次消耗的点数
        BigDecimal pointsDecimal = new BigDecimal(resEachPoints);
        UserPoints userPoints = userPointsMapper.selectOne(
                new QueryWrapper<UserPoints>()
                        .eq("user_id", String.valueOf(userId))
                        .last("limit 1"));
        log.info("获取用户点数对象为:" +userPoints);
        if (null == userPoints){
            josn.put("msg","请先充值点数!");
            return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),ErrorEnum.XMGFAILED.getMsg(),josn);
        }
        log.info("每次消耗点数为：" + pointsDecimal);
        log.info("用户剩余点数为：" + userPoints.getRemainPoints());
        if(pointsDecimal.compareTo(userPoints.getRemainPoints()) > 0){
            josn.put("msg","点数不足，请充值!");
            return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),ErrorEnum.XMGFAILED.getMsg(),josn);
        }

        String createcodeUrl = YmlUtils.get("xmg.api.artcodeCreate");
        String apikey = YmlUtils.get("xmg.api.apikey");
        HashMap headMap = new HashMap();
        HashMap dataMap = new HashMap();
        headMap.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36");
        headMap.put("Content-Type","application/json;charset=utf-8");
        headMap.put("apiKey",apikey);

        dataMap.put("template_id",createCodeValidate.getTemplate_id());
        dataMap.put("callback_url","https://www.baidu.com");
        dataMap.put("qr_image",createCodeValidate.getQr_image());
        int timeout = 120000;
        // 填充map数据
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(dataMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //生成二维码
        String res = HttpClientUtil.doPost(createcodeUrl,headMap,jsonString,timeout);
        log.info("二维码生成结果为：" + res);
        JSONObject json = JSONObject.parseObject(res);
        Integer code = (Integer) json.get("code");
        if(200 == code){
            //todo 这里扣除点数操作
            // 点数转bigdecimal，更新点数
            BigDecimal remainPoints = userPoints.getRemainPoints();
            //减去每次请求所需的点数
            userPoints.setRemainPoints(remainPoints.subtract(pointsDecimal));
            userPointsMapper.updateById(userPoints);
            //新增点数消耗记录
            UserPointsUserecord userPointsUserecord = new UserPointsUserecord();
            userPointsUserecord.setId(SnowIdUtil.getStrUUid());
            userPointsUserecord.setUserId(userId);
            userPointsUserecord.setScene("artCode");
            userPointsUserecord.setConsume(pointsDecimal);
            userPointsUserecord.setCreateTime(new Date());
            userPointsUserecordMapper.insert(userPointsUserecord);
            log.info("点数使用记录更新成功");

            System.out.println(json.get("data"));
            String img_uuid = json.get("data").toString();

            ////任务调度开始
            AiTask aiTask = new AiTask();
            aiTask.setId(SnowIdUtil.getStrUUid());
            aiTask.setUserId(userId);
            aiTask.setName("艺术二维码");
            aiTask.setType(AiEnum.AiArtCode.getValue());
            //设置图片ID到任务中，定时任务需要获取
            aiTask.setBusiness(img_uuid);
            //0表示任务未开始
            aiTask.setStatus("0");
            aiTask.setCreateTime(new Date());
            aiTaskMapper.insert(aiTask);
            josn.put("msg",img_uuid);
            return AjaxResult.success(ErrorEnum.XMGSUCCESS.getCode(),ErrorEnum.XMGSUCCESS.getMsg(),img_uuid);
        }else{
            String msgRes = json.get("msg").toString();
            josn.put("msg",msgRes);
            return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),ErrorEnum.XMGFAILED.getMsg(),josn);
        }


    }


    @GetMapping("/getQrCode")
    @ApiOperation(value="获取艺术二维码")
    public AjaxResult<Object> getQrCode(@Validated @RequestParam("imageId") String imageId) {
//        String getQrCode = YmlUtils.get("xmg.api.getQrCode");
//        String apikey = YmlUtils.get("xmg.api.apikey");
//        HashMap headMap = new HashMap();
//        headMap.put("apiKey",apikey);
//
//        HashMap dataMap = new HashMap();
//        dataMap.put("imageId",imageId);
//        String res = HttpClientUtil.doGetParams(getQrCode,headMap,dataMap,3000);
        AiArtcodeRecordVo aiArtCode = iAiArtService.getQrCode(imageId);
        if(null == aiArtCode){
            return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),ErrorEnum.XMGFAILED.getMsg());
        }else{
            String imgPath = aiArtCode.getLocalUrls();
            return AjaxResult.success(ErrorEnum.XMGSUCCESS.getCode(),ErrorEnum.XMGSUCCESS.getMsg(),imgPath);
        }
//        Integer code = Integer.valueOf(json.get("code").toString());
//        if(200 == code){
//            String data = json.get("data").toString();
//            return AjaxResult.success(ErrorEnum.XMGSUCCESS.getCode(),ErrorEnum.XMGSUCCESS.getMsg(),data);
//        }

    }


    /**
     * 获取可用点数
     * @return
     */
    @GetMapping("/getPionts")
    @ApiOperation(value="获取可用点数")
    public AjaxResult<Object> getPionts() {
        String userIdInt = String.valueOf(LikeFrontThreadLocal.getUserId());
        if(StringUtils.isEmpty(userIdInt)){
            return AjaxResult.failed("获取用户ID失败！");
        }
        BigDecimal remainPoints = iAiArtService.selectPoints(userIdInt);
        return AjaxResult.success(ErrorEnum.XMGSUCCESS.getCode(),ErrorEnum.XMGSUCCESS.getMsg(),remainPoints);
    }



    /**
     * 获取充值记录
     * @return
     */
    @GetMapping("/getChargeRecords")
    @ApiOperation(value="获取充值记录")
    public AjaxResult<Object> getChargeRecords() {
        String userIdInt = String.valueOf(LikeFrontThreadLocal.getUserId());
        if(StringUtils.isEmpty(userIdInt)){
            return AjaxResult.failed("获取用户ID失败！");
        }
        List<UserPointsChargrecordVo> chargrecords = iAiArtService.selectUserRecords(userIdInt);
        return AjaxResult.success(ErrorEnum.XMGSUCCESS.getCode(),ErrorEnum.XMGSUCCESS.getMsg(),chargrecords);
    }


    /**
     * 获取艺术二维码
     * @return
     */
    @GetMapping("/selectArtCode")
    @ApiOperation(value="获取艺术二维码")
    public AjaxResult<Object> selectArtCode() {
        String userIdInt = String.valueOf(LikeFrontThreadLocal.getUserId());
        if(StringUtils.isEmpty(userIdInt)){
            return AjaxResult.failed("获取用户ID失败！");
        }
        List<UserPointsChargrecordVo> chargrecords = iAiArtService.selectUserRecords(userIdInt);
        return AjaxResult.success(ErrorEnum.XMGSUCCESS.getCode(),ErrorEnum.XMGSUCCESS.getMsg(),chargrecords);
    }


    /**
     * 获取用户创作记录
     * @return
     */
    @GetMapping("/getArtRecords")
    @ApiOperation(value="获取创作记录")
    public AjaxResult<Object> getArtRecords(String aiType) {
        String userIdInt = String.valueOf(LikeFrontThreadLocal.getUserId());
        if(StringUtils.isEmpty(userIdInt)){
            return AjaxResult.failed("获取用户ID失败！");
        }
        List<AiArtcodeRecordVo> aiArtcodeRecordVos = iAiArtService.selectArtCodeRecords(userIdInt,aiType);
        return AjaxResult.success(ErrorEnum.XMGSUCCESS.getCode(),ErrorEnum.XMGSUCCESS.getMsg(),aiArtcodeRecordVos);
    }


    /**
     * 获取点数消耗记录
     * @return
     */
    @GetMapping("/getConsumred")
    @ApiOperation(value="获取点数消耗记录")
    public AjaxResult<Object> getConsumred() {
        String userIdInt = String.valueOf(LikeFrontThreadLocal.getUserId());
        if(StringUtils.isEmpty(userIdInt)){
            return AjaxResult.failed("获取用户ID失败！");
        }
        List<UserPointsRecordVo> aiArtcodeRecordVos = iAiArtService.selectPointsConsumred(userIdInt);
        return AjaxResult.success(ErrorEnum.XMGSUCCESS.getCode(),ErrorEnum.XMGSUCCESS.getMsg(),aiArtcodeRecordVos);
    }


    /**
     * 获取所有模型
     * @return
     */
    @NotLogin
    @GetMapping("/getOwnModel")
    @ApiOperation(value="获取所有模型")
    public AjaxResult<Object> getOwnModel() {
        List<AiArtcodeOwnModelVo> models = iAiArtService.selectAllModel();
        if(models.size()>0){
            return AjaxResult.success(ErrorEnum.XMGSUCCESS.getCode(),ErrorEnum.XMGSUCCESS.getMsg(),models);
        }else {
            return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),"模型获取失败！");
        }

    }



    /**
     * 获取用户创作记录列表分页
     * @return
     */
    @PostMapping("/aiList")
    @ApiOperation(value="用户创作记录")
    public AjaxResult<Object> aiList(@Validated @RequestBody PageValidate pageValidate) {
        String aiType = pageValidate.getAiType();
        if(StringUtils.isBlank(aiType)){
            return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),"AI类型不能为空！");
        }
        String userIdInt = String.valueOf(LikeFrontThreadLocal.getUserId());
        if(StringUtils.isEmpty(userIdInt)){
            return AjaxResult.failed(ErrorEnum.XMGFAILED.getCode(),"用户ID不能为空！");
        }
        pageValidate.setUserId(userIdInt);
        PageResult<AiRecordListedVo> aiArtcodeRecordVos = iAiArtService.list(pageValidate);
        return AjaxResult.success(ErrorEnum.XMGSUCCESS.getCode(),ErrorEnum.XMGSUCCESS.getMsg(),aiArtcodeRecordVos);
    }






}
