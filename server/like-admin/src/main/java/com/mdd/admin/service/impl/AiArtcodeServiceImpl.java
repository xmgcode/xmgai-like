package com.mdd.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdd.admin.service.IAiArtcodeService;
import com.mdd.admin.validate.ai.artcode.AiArtcodeCreateValidate;
import com.mdd.admin.validate.ai.artcode.AiArtcodeSearchValidate;
import com.mdd.admin.validate.ai.artcode.AiArtcodeUpdateValidate;
import com.mdd.admin.validate.ai.artcode.AiQrModelCreateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.ai.artcode.AiArtcodeDetailVo;
import com.mdd.admin.vo.ai.artcode.AiArtcodeListedVo;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.ai.AiArtcode;
import com.mdd.common.entity.ai.AiTask;
import com.mdd.common.entity.user.User;
import com.mdd.common.entity.user.UserPoints;
import com.mdd.common.enums.AiEnum;
import com.mdd.common.mapper.ai.AiArtcodeMapper;
import com.mdd.common.mapper.ai.AiTaskMapper;
import com.mdd.common.mapper.user.UserMapper;
import com.mdd.common.mapper.user.UserPointsMapper;
import com.mdd.common.util.HttpClientUtil;
import com.mdd.common.util.SnowIdUtil;
import com.mdd.common.util.YmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 二维码生成实现类
 * @author xmg
 */
@Service
@Slf4j
public class AiArtcodeServiceImpl implements IAiArtcodeService {
        
    @Resource
    AiArtcodeMapper aiArtcodeMapper;

    @Resource
    AiTaskMapper aiTaskMapper;

    @Resource
    UserPointsMapper userPointsMapper;

    @Resource
    UserMapper userMapper;

    /**
     * 二维码生成列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<AiArtcodeListedVo>
     */
    @Override
    public PageResult<AiArtcodeListedVo> list(PageValidate pageValidate, AiArtcodeSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        QueryWrapper<AiArtcode> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        aiArtcodeMapper.setSearch(queryWrapper, searchValidate, new String[]{
            "=:userId@user_id:str",
            "=:urls:str",
            "=:duration:str",
            "=:cost:str",
            "=:mode:str",
            "=:callbackOriginUrl@callback_origin_url:str",
            "=:status:str",
        });

        IPage<AiArtcode> iPage = aiArtcodeMapper.selectPage(new Page<>(page, limit), queryWrapper);

        List<AiArtcodeListedVo> list = new LinkedList<>();
        for(AiArtcode item : iPage.getRecords()) {
            AiArtcodeListedVo vo = new AiArtcodeListedVo();
            BeanUtils.copyProperties(item, vo);
            vo.setCreateTime(item.getCreateTime());
            list.add(vo);
        }

        return PageResult.iPageHandle(iPage.getTotal(), iPage.getCurrent(), iPage.getSize(), list);
    }

    /**
     * 二维码生成详情
     *
     * @author xmg
     * @param id 主键参数
     * @return AiArtcode
     */
    @Override
    public AiArtcodeDetailVo detail(String id) {
        AiArtcode model = aiArtcodeMapper.selectOne(
                new QueryWrapper<AiArtcode>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在");

        AiArtcodeDetailVo vo = new AiArtcodeDetailVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    /**
     * 二维码生成新增
     *
     * @author xmg
     * @param createValidate 参数
     */
    @Override
    public void add(AiArtcodeCreateValidate createValidate) {
        AiArtcode model = new AiArtcode();
        model.setImgUuid(createValidate.getImgUuid());
        model.setUserId(createValidate.getUserId());
        model.setUrls(createValidate.getUrls());
        model.setLocalUrls(createValidate.getLocalUrls());
        model.setDuration(createValidate.getDuration());
        model.setCost(createValidate.getCost());
        model.setMode(createValidate.getMode());
        model.setCallbackOriginUrl(createValidate.getCallbackOriginUrl());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        aiArtcodeMapper.insert(model);
    }

    /**
     * 二维码生成编辑
     *
     * @author xmg
     * @param updateValidate 参数
     */
    @Override
    public void edit(AiArtcodeUpdateValidate updateValidate) {
        AiArtcode model = aiArtcodeMapper.selectOne(
                new QueryWrapper<AiArtcode>()
                    .eq("id",  updateValidate.getId())
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        model.setId(updateValidate.getId());
        model.setImgUuid(updateValidate.getImgUuid());
        model.setUserId(updateValidate.getUserId());
        model.setUrls(updateValidate.getUrls());
        model.setLocalUrls(updateValidate.getLocalUrls());
        model.setDuration(updateValidate.getDuration());
        model.setCost(updateValidate.getCost());
        model.setMode(updateValidate.getMode());
        model.setCallbackOriginUrl(updateValidate.getCallbackOriginUrl());
        model.setStatus(updateValidate.getStatus());
        model.setUpdateTime(new Date());
        aiArtcodeMapper.updateById(model);
    }

    /**
     * 二维码生成删除
     *
     * @author xmg
     * @param id 主键ID
     */
    @Override
    public void del(String id) {
        AiArtcode model = aiArtcodeMapper.selectOne(
                new QueryWrapper<AiArtcode>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        aiArtcodeMapper.delete(new QueryWrapper<AiArtcode>().eq("id", id));
    }

    @Override
    public JSONObject createQrCode(AiQrModelCreateValidate createCodeValidate, HttpServletRequest request) {
        String userApiKey = request.getHeader("apiKey");
        JSONObject reJson = new JSONObject();
        String url = YmlUtils.get("ai.artcode.qrCodeUrl");
        String apiKey = YmlUtils.get("ai.artcode.apiKey");
        String resEachPoints = YmlUtils.get("ai.artcode.eachPoints");
        BigDecimal pointsDecimal = new BigDecimal(resEachPoints);

        User userRes = userMapper.selectOne(
                new QueryWrapper<User>()
                        .eq("api_key", userApiKey)
                        .last("limit 1"));
        UserPoints userPoints = userPointsMapper.selectOne(
                new QueryWrapper<UserPoints>()
                        .eq("user_id", userRes.getId())
                        .last("limit 1"));
        HashMap headMap = new HashMap();
        HashMap dataMap = new HashMap();
        headMap.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36");
        headMap.put("Content-Type","application/json;charset=utf-8");
        headMap.put("Authorization",apiKey);

        dataMap.put("prompt",createCodeValidate.getPrompt());
        dataMap.put("template_id",createCodeValidate.getTemplate_id());
        dataMap.put("callback_url",createCodeValidate.getCallback_url());
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
        String res = HttpClientUtil.doPost(url,headMap,jsonString,timeout);
        log.info("调用三方接口生成二维码ID返回信息为：" + res);
        JSONObject imageObj = JSON.parseObject(res);
        String resCode = imageObj.get("code").toString();
        String img_uuid = "";
        //0表示调用成功
        if("0".equals(resCode)){
            // 点数转bigdecimal，更新点数
            BigDecimal remainPoints = userPoints.getRemainPoints();
            //减去每次请求所需的点数
            userPoints.setRemainPoints(remainPoints.subtract(pointsDecimal));
            userPointsMapper.updateById(userPoints);
            JSONObject dataObj = (JSONObject)imageObj.get("data");
            img_uuid = dataObj.get("img_uuid").toString();
            log.info("二维码ID：" + img_uuid);
            //任务调度开始
            AiTask aiTask = new AiTask();
            aiTask.setId(SnowIdUtil.getStrUUid());
            aiTask.setName("艺术二维码");
            aiTask.setType(AiEnum.AiArtCode.getValue());
            //设置图片ID到任务中，定时任务需要获取
            aiTask.setBusiness(img_uuid);
            //0表示任务未开始
            aiTask.setStatus("0");
            aiTask.setCreateTime(new Date());
            aiTaskMapper.insert(aiTask);
        }
        reJson.put("code","200");
        reJson.put("msg","二维码生成成功！");
        reJson.put("img_uuid",img_uuid);
        return reJson;
    }

    @Override
    public String getQrCode(String imageId) {
        AiArtcode model = aiArtcodeMapper.selectOne(
                new QueryWrapper<AiArtcode>()
                        .eq("img_uuid", imageId)
                        .last("limit 1"));
        Assert.notNull(model, "数据不存在!");
        return model.getLocalUrls();
    }


    public static MultipartFile createMultipartFileFromUrl(String imageUrl) throws IOException {
        // 从URL获取InputStream
        try (InputStream is = new URL(imageUrl).openStream()) {
            // 将InputStream转换为byte数组
            byte[] fileData = Files.readAllBytes(Paths.get(is.toString()));

            // 创建MultipartFile实例
            return new MockMultipartFile(
                    "image.png", // 可以选择一个文件名
                    "image.png", // 原始文件名
                    "image/png", // 内容类型
                    fileData // 文件数据
            );
        }
    }

}
