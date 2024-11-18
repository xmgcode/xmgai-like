package com.mdd.admin.controller.ai;

import com.mdd.admin.aop.Log;
import com.mdd.admin.service.IAiArtcodeModelService;
import com.mdd.admin.validate.ai.model.AiArtcodeModelCreateValidate;
import com.mdd.admin.validate.ai.model.AiArtcodeModelSearchValidate;
import com.mdd.admin.validate.ai.model.AiArtcodeModelUpdateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.ai.model.AiArtcodeModelDetailVo;
import com.mdd.admin.vo.ai.model.AiArtcodeModelListedVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.util.HttpClientUtil;
import com.mdd.common.util.YmlUtils;
import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("api/artcode")
@Api(tags = "艺术二维码模板管理")
public class AiArtcodeModelController {

    @Resource
    IAiArtcodeModelService iAiArtcodeModelService;

    @GetMapping("/list")
    @ApiOperation(value="艺术二维码模板列表")
    public AjaxResult<PageResult<AiArtcodeModelListedVo>> list(@Validated PageValidate pageValidate,
                                                               @Validated AiArtcodeModelSearchValidate searchValidate) {
        PageResult<AiArtcodeModelListedVo> list = iAiArtcodeModelService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    @ApiOperation(value="艺术二维码模板详情")
    public AjaxResult<AiArtcodeModelDetailVo> detail(@Validated @IDMust() @RequestParam("id") String id) {
        AiArtcodeModelDetailVo detail = iAiArtcodeModelService.detail(id);
        return AjaxResult.success(detail);
    }

    @Log(title = "艺术二维码模板新增")
    @PostMapping("/add")
    @ApiOperation(value="艺术二维码模板新增")
    public AjaxResult<Object> add(@Validated @RequestBody AiArtcodeModelCreateValidate createValidate) {
        iAiArtcodeModelService.add(createValidate);
        return AjaxResult.success();
    }

    @Log(title = "艺术二维码模板编辑")
    @PostMapping("/edit")
    @ApiOperation(value="艺术二维码模板编辑")
    public AjaxResult<Object> edit(@Validated @RequestBody AiArtcodeModelUpdateValidate updateValidate) {
        iAiArtcodeModelService.edit(updateValidate);
        return AjaxResult.success();
    }

    @Log(title = "艺术二维码模板删除")
    @PostMapping("/del")
    @ApiOperation(value="艺术二维码模板删除")
    public AjaxResult<Object> del(@RequestBody String id) {
        iAiArtcodeModelService.del(id);
        return AjaxResult.success();
    }










}
