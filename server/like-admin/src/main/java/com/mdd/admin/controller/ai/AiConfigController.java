package com.mdd.admin.controller.ai;

import com.mdd.admin.aop.Log;
import com.mdd.admin.service.IAiConfigService;
import com.mdd.admin.validate.ai.aiconfig.AiConfigCreateValidate;
import com.mdd.admin.validate.ai.aiconfig.AiConfigSearchValidate;
import com.mdd.admin.validate.ai.aiconfig.AiConfigUpdateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.ai.aiconfig.AiConfigDetailVo;
import com.mdd.admin.vo.ai.aiconfig.AiConfigListedVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/aiconfig")
@Api(tags = "AI配置管理")
public class AiConfigController {

    @Resource
    IAiConfigService iAiConfigService;

    @GetMapping("/list")
    @ApiOperation(value="AI配置列表")
    public AjaxResult<PageResult<AiConfigListedVo>> list(@Validated PageValidate pageValidate,
                                                         @Validated AiConfigSearchValidate searchValidate) {
        PageResult<AiConfigListedVo> list = iAiConfigService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    @ApiOperation(value="AI配置详情")
    public AjaxResult<AiConfigDetailVo> detail(@Validated @IDMust() @RequestParam("id") String id) {
        AiConfigDetailVo detail = iAiConfigService.detail(id);
        return AjaxResult.success(detail);
    }

    @Log(title = "AI配置新增")
    @PostMapping("/add")
    @ApiOperation(value="AI配置新增")
    public AjaxResult<Object> add(@Validated @RequestBody AiConfigCreateValidate createValidate) {
        iAiConfigService.add(createValidate);
        return AjaxResult.success();
    }

    @Log(title = "AI配置编辑")
    @PostMapping("/edit")
    @ApiOperation(value="AI配置编辑")
    public AjaxResult<Object> edit(@Validated @RequestBody AiConfigUpdateValidate updateValidate) {
        iAiConfigService.edit(updateValidate);
        return AjaxResult.success();
    }

    @Log(title = "AI配置删除")
    @PostMapping("/del")
    @ApiOperation(value="AI配置删除")
    public AjaxResult<Object> del(@RequestBody String id) {
        iAiConfigService.del(id);
        return AjaxResult.success();
    }

}
