package com.mdd.admin.controller.user;

import com.mdd.admin.aop.Log;
import com.mdd.admin.service.IUserPointsService;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.user.UserPointsCreateValidate;
import com.mdd.admin.validate.user.UserPointsSearchValidate;
import com.mdd.admin.validate.user.UserPointsUpdateValidate;
import com.mdd.admin.vo.user.UserPointsDetailVo;
import com.mdd.admin.vo.user.UserPointsListedVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/points")
@Api(tags = "用户点数管理")
public class UserPointsController {

    @Resource
    IUserPointsService iUserPointsService;

    @GetMapping("/list")
    @ApiOperation(value="用户点数列表")
    public AjaxResult<PageResult<UserPointsListedVo>> list(@Validated PageValidate pageValidate,
                                                           @Validated UserPointsSearchValidate searchValidate) {
        PageResult<UserPointsListedVo> list = iUserPointsService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    @ApiOperation(value="用户点数详情")
    public AjaxResult<UserPointsDetailVo> detail(@Validated @IDMust() @RequestParam("id") String id) {
        UserPointsDetailVo detail = iUserPointsService.detail(id);
        return AjaxResult.success(detail);
    }

    @Log(title = "用户点数新增")
    @PostMapping("/add")
    @ApiOperation(value="用户点数新增")
    public AjaxResult<Object> add(@Validated @RequestBody UserPointsCreateValidate createValidate) {
        iUserPointsService.add(createValidate);
        return AjaxResult.success();
    }

    @Log(title = "用户点数编辑")
    @PostMapping("/edit")
    @ApiOperation(value="用户点数编辑")
    public AjaxResult<Object> edit(@Validated @RequestBody UserPointsUpdateValidate updateValidate) {
        iUserPointsService.edit(updateValidate);
        return AjaxResult.success();
    }

    @Log(title = "用户点数删除")
    @PostMapping("/del")
    @ApiOperation(value="用户点数删除")
    public AjaxResult<Object> del(@RequestBody String id) {
        iUserPointsService.del(id);
        return AjaxResult.success();
    }

}
