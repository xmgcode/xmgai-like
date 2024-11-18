package com.mdd.admin.controller.user;

import com.mdd.admin.aop.Log;
import com.mdd.admin.service.IUserPointsUserecordService;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.user.UserPointsUserecordCreateValidate;
import com.mdd.admin.validate.user.UserPointsUserecordSearchValidate;
import com.mdd.admin.validate.user.UserPointsUserecordUpdateValidate;
import com.mdd.admin.vo.user.UserPointsUserecordDetailVo;
import com.mdd.admin.vo.user.UserPointsUserecordListedVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/userecord")
@Api(tags = "使用记录管理")
public class UserPointsUserecordController {

    @Resource
    IUserPointsUserecordService iUserPointsUserecordService;

    @GetMapping("/list")
    @ApiOperation(value="使用记录列表")
    public AjaxResult<PageResult<UserPointsUserecordListedVo>> list(@Validated PageValidate pageValidate,
                                                                    @Validated UserPointsUserecordSearchValidate searchValidate) {
        PageResult<UserPointsUserecordListedVo> list = iUserPointsUserecordService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    @ApiOperation(value="使用记录详情")
    public AjaxResult<UserPointsUserecordDetailVo> detail(@Validated @IDMust() @RequestParam("id") String id) {
        UserPointsUserecordDetailVo detail = iUserPointsUserecordService.detail(id);
        return AjaxResult.success(detail);
    }

    @Log(title = "使用记录新增")
    @PostMapping("/add")
    @ApiOperation(value="使用记录新增")
    public AjaxResult<Object> add(@Validated @RequestBody UserPointsUserecordCreateValidate createValidate) {
        iUserPointsUserecordService.add(createValidate);
        return AjaxResult.success();
    }

    @Log(title = "使用记录编辑")
    @PostMapping("/edit")
    @ApiOperation(value="使用记录编辑")
    public AjaxResult<Object> edit(@Validated @RequestBody UserPointsUserecordUpdateValidate updateValidate) {
        iUserPointsUserecordService.edit(updateValidate);
        return AjaxResult.success();
    }

    @Log(title = "使用记录删除")
    @PostMapping("/del")
    @ApiOperation(value="使用记录删除")
    public AjaxResult<Object> del(@RequestBody String id) {
        iUserPointsUserecordService.del(id);
        return AjaxResult.success();
    }

}
