package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdd.admin.service.IAiArtcodeModelService;
import com.mdd.admin.validate.ai.model.AiArtcodeModelCreateValidate;
import com.mdd.admin.validate.ai.model.AiArtcodeModelSearchValidate;
import com.mdd.admin.validate.ai.model.AiArtcodeModelUpdateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.ai.model.AiArtcodeModelDetailVo;
import com.mdd.admin.vo.ai.model.AiArtcodeModelListedVo;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.ai.AiArtcodeModel;
import com.mdd.common.mapper.ai.AiArtcodeModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 艺术二维码模板实现类
 * @author xmg
 */
@Service
public class AiArtcodeModelServiceImpl implements IAiArtcodeModelService {
        
    @Resource
    AiArtcodeModelMapper aiArtcodeModelMapper;

    /**
     * 艺术二维码模板列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<AiArtcodeModelListedVo>
     */
    @Override
    public PageResult<AiArtcodeModelListedVo> list(PageValidate pageValidate, AiArtcodeModelSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        QueryWrapper<AiArtcodeModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        aiArtcodeModelMapper.setSearch(queryWrapper, searchValidate, new String[]{
            "like:modelNum@model_num:str",
            "like:name:str",
        });

        IPage<AiArtcodeModel> iPage = aiArtcodeModelMapper.selectPage(new Page<>(page, limit), queryWrapper);

        List<AiArtcodeModelListedVo> list = new LinkedList<>();
        for(AiArtcodeModel item : iPage.getRecords()) {
            AiArtcodeModelListedVo vo = new AiArtcodeModelListedVo();
            BeanUtils.copyProperties(item, vo);
            list.add(vo);
        }

        return PageResult.iPageHandle(iPage.getTotal(), iPage.getCurrent(), iPage.getSize(), list);
    }

    /**
     * 艺术二维码模板详情
     *
     * @author xmg
     * @param id 主键参数
     * @return AiArtcodeModel
     */
    @Override
    public AiArtcodeModelDetailVo detail(String id) {
        AiArtcodeModel model = aiArtcodeModelMapper.selectOne(
                new QueryWrapper<AiArtcodeModel>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在");

        AiArtcodeModelDetailVo vo = new AiArtcodeModelDetailVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    /**
     * 艺术二维码模板新增
     *
     * @author xmg
     * @param createValidate 参数
     */
    @Override
    public void add(AiArtcodeModelCreateValidate createValidate) {
        AiArtcodeModel model = new AiArtcodeModel();
        model.setModelNum(createValidate.getModelNum());
        model.setName(createValidate.getName());
        model.setPreviewImg(createValidate.getPreviewImg());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        aiArtcodeModelMapper.insert(model);
    }

    /**
     * 艺术二维码模板编辑
     *
     * @author xmg
     * @param updateValidate 参数
     */
    @Override
    public void edit(AiArtcodeModelUpdateValidate updateValidate) {
        AiArtcodeModel model = aiArtcodeModelMapper.selectOne(
                new QueryWrapper<AiArtcodeModel>()
                    .eq("id",  updateValidate.getId())
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        model.setId(updateValidate.getId());
        model.setModelNum(updateValidate.getModelNum());
        model.setName(updateValidate.getName());
        model.setPreviewImg(updateValidate.getPreviewImg());
        model.setUpdateTime(new Date());
        aiArtcodeModelMapper.updateById(model);
    }

    /**
     * 艺术二维码模板删除
     *
     * @author xmg
     * @param id 主键ID
     */
    @Override
    public void del(String id) {
        AiArtcodeModel model = aiArtcodeModelMapper.selectOne(
                new QueryWrapper<AiArtcodeModel>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        aiArtcodeModelMapper.delete(new QueryWrapper<AiArtcodeModel>().eq("id", id));
    }



}
