package com.mdd.admin.controller.ai;

import com.mdd.admin.aop.Log;
import com.mdd.admin.service.IAiTaskService;
import com.mdd.admin.validate.ai.task.AiTaskCreateValidate;
import com.mdd.admin.validate.ai.task.AiTaskSearchValidate;
import com.mdd.admin.validate.ai.task.AiTaskUpdateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.ai.task.AiTaskDetailVo;
import com.mdd.admin.vo.ai.task.AiTaskListedVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/task")
@Api(tags = "任务调度管理")
public class AiTaskController {

    @Resource
    IAiTaskService iAiTaskService;

    @GetMapping("/list")
    @ApiOperation(value="任务调度列表")
    public AjaxResult<PageResult<AiTaskListedVo>> list(@Validated PageValidate pageValidate,
                                                       @Validated AiTaskSearchValidate searchValidate) {
        PageResult<AiTaskListedVo> list = iAiTaskService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    @ApiOperation(value="任务调度详情")
    public AjaxResult<AiTaskDetailVo> detail(@Validated @IDMust() @RequestParam("id") String id) {
        AiTaskDetailVo detail = iAiTaskService.detail(id);
        return AjaxResult.success(detail);
    }

    @Log(title = "任务调度新增")
    @PostMapping("/add")
    @ApiOperation(value="任务调度新增")
    public AjaxResult<Object> add(@Validated @RequestBody AiTaskCreateValidate createValidate) {
        iAiTaskService.add(createValidate);
        return AjaxResult.success();
    }

    @Log(title = "任务调度编辑")
    @PostMapping("/edit")
    @ApiOperation(value="任务调度编辑")
    public AjaxResult<Object> edit(@Validated @RequestBody AiTaskUpdateValidate updateValidate) {
        iAiTaskService.edit(updateValidate);
        return AjaxResult.success();
    }

    @Log(title = "任务调度删除")
    @PostMapping("/del")
    @ApiOperation(value="任务调度删除")
    public AjaxResult<Object> del(@RequestBody String id) {
        iAiTaskService.del(id);
        return AjaxResult.success();
    }

}
