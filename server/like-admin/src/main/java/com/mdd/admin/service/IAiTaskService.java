package com.mdd.admin.service;

import com.mdd.admin.validate.ai.task.AiTaskCreateValidate;
import com.mdd.admin.validate.ai.task.AiTaskSearchValidate;
import com.mdd.admin.validate.ai.task.AiTaskUpdateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.ai.task.AiTaskDetailVo;
import com.mdd.admin.vo.ai.task.AiTaskListedVo;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.ai.AiTask;

/**
 * 任务调度服务接口类
 * @author xmg
 */
public interface IAiTaskService {

    /**
     * 任务调度列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<AiTaskListedVo>
     */
    PageResult<AiTaskListedVo> list(PageValidate pageValidate, AiTaskSearchValidate searchValidate);

    /**
     * 任务调度详情
     *
     * @author xmg
     * @param id 主键ID
     * @return AiTaskDetailVo
     */
    AiTaskDetailVo detail(String id);

    /**
     * 任务调度新增
     *
     * @author xmg
     * @param createValidate 参数
     */
    void add(AiTaskCreateValidate createValidate);

    /**
     * 任务调度编辑
     *
     * @author xmg
     * @param updateValidate 参数
     */
    void edit(AiTaskUpdateValidate updateValidate);

    /**
     * 任务调度删除
     *
     * @author xmg
     * @param id 主键ID
     */
    void del(String id);

    /**
     * 任务调度新增
     *
     * @author xmg
     * @param aiTask 参数
     */
    void insert(AiTask aiTask);

}
