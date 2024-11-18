package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdd.admin.service.IAiTaskService;
import com.mdd.admin.validate.ai.task.AiTaskCreateValidate;
import com.mdd.admin.validate.ai.task.AiTaskSearchValidate;
import com.mdd.admin.validate.ai.task.AiTaskUpdateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.ai.task.AiTaskDetailVo;
import com.mdd.admin.vo.ai.task.AiTaskListedVo;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.ai.AiTask;
import com.mdd.common.mapper.ai.AiTaskMapper;
import com.mdd.common.util.SnowIdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 任务调度实现类
 * @author xmg
 */
@Service
public class AiTaskServiceImpl implements IAiTaskService {
        
    @Resource
    AiTaskMapper aiTaskMapper;

    /**
     * 任务调度列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<AiTaskListedVo>
     */
    @Override
    public PageResult<AiTaskListedVo> list(PageValidate pageValidate, AiTaskSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        QueryWrapper<AiTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        aiTaskMapper.setSearch(queryWrapper, searchValidate, new String[]{
            "like:name:str",
            "=:status:str",
            "=:type:str",
            "=:business:str",
            "datetime:startTimeStart-startTimeEnd@start_time:str",
            "datetime:endTimeStart-endTimeEnd@end_time:str",
            "=:totalTime@total_time:int",
            "=:deleted:int",
        });

        IPage<AiTask> iPage = aiTaskMapper.selectPage(new Page<>(page, limit), queryWrapper);

        List<AiTaskListedVo> list = new LinkedList<>();
        for(AiTask item : iPage.getRecords()) {
            AiTaskListedVo vo = new AiTaskListedVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUpdateTime(item.getUpdateTime());
            vo.setStartTime(item.getStartTime());
            vo.setEndTime(item.getEndTime());
            list.add(vo);
        }

        return PageResult.iPageHandle(iPage.getTotal(), iPage.getCurrent(), iPage.getSize(), list);
    }

    /**
     * 任务调度详情
     *
     * @author xmg
     * @param id 主键参数
     * @return AiTask
     */
    @Override
    public AiTaskDetailVo detail(String id) {
        AiTask model = aiTaskMapper.selectOne(
                new QueryWrapper<AiTask>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在");

        AiTaskDetailVo vo = new AiTaskDetailVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    /**
     * 任务调度新增
     *
     * @author xmg
     * @param createValidate 参数
     */
    @Override
    public void add(AiTaskCreateValidate createValidate) {
        AiTask model = new AiTask();
        model.setName(createValidate.getName());
        model.setStatus(createValidate.getStatus());
        model.setType(createValidate.getType());
        model.setBusiness(createValidate.getBusiness());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        model.setDeleted(createValidate.getDeleted());
        aiTaskMapper.insert(model);
    }

    /**
     * 任务调度编辑
     *
     * @author xmg
     * @param updateValidate 参数
     */
    @Override
    public void edit(AiTaskUpdateValidate updateValidate) {
        AiTask model = aiTaskMapper.selectOne(
                new QueryWrapper<AiTask>()
                    .eq("id",  updateValidate.getId())
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        model.setId(updateValidate.getId());
        model.setName(updateValidate.getName());
        model.setStatus(updateValidate.getStatus());
        model.setType(updateValidate.getType());
        model.setBusiness(updateValidate.getBusiness());
        model.setUpdateTime(new Date());
        model.setDeleted(updateValidate.getDeleted());
        aiTaskMapper.updateById(model);
    }

    /**
     * 任务调度删除
     *
     * @author xmg
     * @param id 主键ID
     */
    @Override
    public void del(String id) {
        AiTask model = aiTaskMapper.selectOne(
                new QueryWrapper<AiTask>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        aiTaskMapper.delete(new QueryWrapper<AiTask>().eq("id", id));
    }

    @Override
    public void insert(AiTask aiTask) {
        aiTask.setId(SnowIdUtil.getStrUUid());
        aiTask.setStatus("0");
        aiTask.setCreateTime(new Date());
        aiTask.setDeleted(0);
        aiTaskMapper.insert(aiTask);
    }

}
