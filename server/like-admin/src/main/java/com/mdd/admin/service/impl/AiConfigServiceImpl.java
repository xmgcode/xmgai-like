package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdd.admin.service.IAiConfigService;
import com.mdd.admin.validate.ai.aiconfig.AiConfigCreateValidate;
import com.mdd.admin.validate.ai.aiconfig.AiConfigSearchValidate;
import com.mdd.admin.validate.ai.aiconfig.AiConfigUpdateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.ai.aiconfig.AiConfigDetailVo;
import com.mdd.admin.vo.ai.aiconfig.AiConfigListedVo;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.ai.AiConfig;
import com.mdd.common.mapper.ai.AiConfigMapper;
import com.mdd.common.util.SnowIdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * AI配置实现类
 * @author xmg
 */
@Service
public class AiConfigServiceImpl implements IAiConfigService {
        
    @Resource
    AiConfigMapper aiConfigMapper;

    /**
     * AI配置列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<AiConfigListedVo>
     */
    @Override
    public PageResult<AiConfigListedVo> list(PageValidate pageValidate, AiConfigSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        QueryWrapper<AiConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        aiConfigMapper.setSearch(queryWrapper, searchValidate, new String[]{
            "=:type:str",
            "like:name:str",
            "=:value:str",
        });

        IPage<AiConfig> iPage = aiConfigMapper.selectPage(new Page<>(page, limit), queryWrapper);

        List<AiConfigListedVo> list = new LinkedList<>();
        for(AiConfig item : iPage.getRecords()) {
            AiConfigListedVo vo = new AiConfigListedVo();
            BeanUtils.copyProperties(item, vo);
            vo.setCreateTime(item.getCreateTime());
            vo.setUpdateTime(item.getUpdateTime());
            list.add(vo);
        }

        return PageResult.iPageHandle(iPage.getTotal(), iPage.getCurrent(), iPage.getSize(), list);
    }

    /**
     * AI配置详情
     *
     * @author xmg
     * @param id 主键参数
     * @return AiConfig
     */
    @Override
    public AiConfigDetailVo detail(String id) {
        AiConfig model = aiConfigMapper.selectOne(
                new QueryWrapper<AiConfig>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在");

        AiConfigDetailVo vo = new AiConfigDetailVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    /**
     * AI配置新增
     *
     * @author xmg
     * @param createValidate 参数
     */
    @Override
    public void add(AiConfigCreateValidate createValidate) {
        AiConfig model = new AiConfig();
        model.setId(SnowIdUtil.getStrUUid());
        model.setType(createValidate.getType());
        model.setName(createValidate.getName());
        model.setValue(createValidate.getValue());
        model.setRemark(createValidate.getRemark());
        model.setCreateTime(new Date());
        aiConfigMapper.insert(model);
    }

    /**
     * AI配置编辑
     *
     * @author xmg
     * @param updateValidate 参数
     */
    @Override
    public void edit(AiConfigUpdateValidate updateValidate) {
        AiConfig model = aiConfigMapper.selectOne(
                new QueryWrapper<AiConfig>()
                    .eq("id",  updateValidate.getId())
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");
        model.setType(updateValidate.getType());
        model.setName(updateValidate.getName());
        model.setValue(updateValidate.getValue());
        model.setRemark(updateValidate.getRemark());
        model.setUpdateTime(new Date());
        aiConfigMapper.updateById(model);
    }

    /**
     * AI配置删除
     *
     * @author xmg
     * @param id 主键ID
     */
    @Override
    public void del(String id) {
        AiConfig model = aiConfigMapper.selectOne(
                new QueryWrapper<AiConfig>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        aiConfigMapper.delete(new QueryWrapper<AiConfig>().eq("id", id));
    }

    @Override
    public AiConfigDetailVo getByName(String name) {
        AiConfig model = aiConfigMapper.selectOne(
                new QueryWrapper<AiConfig>()
                        .eq("name",  name)
                        .last("limit 1"));
        AiConfigDetailVo vo = new AiConfigDetailVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }



}
