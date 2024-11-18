package com.mdd.admin.controller.ai;

import com.alibaba.fastjson.JSONObject;
import com.mdd.admin.aop.Log;
import com.mdd.admin.service.IAiArtcodeService;
import com.mdd.admin.service.IUserPointsService;
import com.mdd.admin.service.IUserService;
import com.mdd.admin.validate.ai.artcode.AiArtcodeCreateValidate;
import com.mdd.admin.validate.ai.artcode.AiArtcodeSearchValidate;
import com.mdd.admin.validate.ai.artcode.AiArtcodeUpdateValidate;
import com.mdd.admin.validate.ai.artcode.AiQrModelCreateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.ai.artcode.AiArtcodeDetailVo;
import com.mdd.admin.vo.ai.artcode.AiArtcodeListedVo;
import com.mdd.common.aop.NotLogin;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.user.User;
import com.mdd.common.util.ToolUtils;
import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("api/artqrcode")
@Api(tags = "二维码生成管理")
public class AiArtcodeController {

    @Resource
    IAiArtcodeService iAiArtcodeService;

    @Resource
    IUserService iUserService;

    @Resource
    IUserPointsService iUserPointsService;

    @GetMapping("/list")
    @ApiOperation(value="二维码生成列表")
    public AjaxResult<PageResult<AiArtcodeListedVo>> list(@Validated PageValidate pageValidate,
                                                          @Validated AiArtcodeSearchValidate searchValidate) {
        PageResult<AiArtcodeListedVo> list = iAiArtcodeService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    @ApiOperation(value="二维码生成详情")
    public AjaxResult<AiArtcodeDetailVo> detail(@Validated @IDMust() @RequestParam("id") String id) {
        AiArtcodeDetailVo detail = iAiArtcodeService.detail(id);
        return AjaxResult.success(detail);
    }

    @Log(title = "二维码生成新增")
    @PostMapping("/add")
    @ApiOperation(value="二维码生成新增")
    public AjaxResult<Object> add(@Validated @RequestBody AiArtcodeCreateValidate createValidate) {
        iAiArtcodeService.add(createValidate);
        return AjaxResult.success();
    }

    @Log(title = "二维码生成编辑")
    @PostMapping("/edit")
    @ApiOperation(value="二维码生成编辑")
    public AjaxResult<Object> edit(@Validated @RequestBody AiArtcodeUpdateValidate updateValidate) {
        iAiArtcodeService.edit(updateValidate);
        return AjaxResult.success();
    }

    @Log(title = "二维码生成删除")
    @PostMapping("/del")
    @ApiOperation(value="二维码生成删除")
    public AjaxResult<Object> del(@RequestBody String id) {
        iAiArtcodeService.del(id);
        return AjaxResult.success();
    }


}
