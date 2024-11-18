package com.mdd.admin.controller.user;

import com.mdd.admin.aop.Log;
import com.mdd.admin.service.IUserPointsChargrecordService;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.user.UserPointsChargrecordCreateValidate;
import com.mdd.admin.validate.user.UserPointsChargrecordSearchValidate;
import com.mdd.admin.validate.user.UserPointsChargrecordUpdateValidate;
import com.mdd.admin.vo.user.UserPointsChargrecordDetailVo;
import com.mdd.admin.vo.user.UserPointsChargrecordListedVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/chargrecord")
@Api(tags = "充值记录管理")
public class UserPointsChargrecordController {

    @Resource
    IUserPointsChargrecordService iUserPointsChargrecordService;

    @GetMapping("/list")
    @ApiOperation(value="充值记录列表")
    public AjaxResult<PageResult<UserPointsChargrecordListedVo>> list(@Validated PageValidate pageValidate,
                                                     @Validated UserPointsChargrecordSearchValidate searchValidate) {
        PageResult<UserPointsChargrecordListedVo> list = iUserPointsChargrecordService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    @ApiOperation(value="充值记录详情")
    public AjaxResult<UserPointsChargrecordDetailVo> detail(@Validated @IDMust() @RequestParam("id") String id) {
        UserPointsChargrecordDetailVo detail = iUserPointsChargrecordService.detail(id);
        return AjaxResult.success(detail);
    }

    @Log(title = "充值记录新增")
    @PostMapping("/add")
    @ApiOperation(value="充值记录新增")
    public AjaxResult<Object> add(@Validated @RequestBody UserPointsChargrecordCreateValidate createValidate) {
        iUserPointsChargrecordService.add(createValidate);
        return AjaxResult.success();
    }

    @Log(title = "充值记录编辑")
    @PostMapping("/edit")
    @ApiOperation(value="充值记录编辑")
    public AjaxResult<Object> edit(@Validated @RequestBody UserPointsChargrecordUpdateValidate updateValidate) {
        iUserPointsChargrecordService.edit(updateValidate);
        return AjaxResult.success();
    }

    @Log(title = "充值记录删除")
    @PostMapping("/del")
    @ApiOperation(value="充值记录删除")
    public AjaxResult<Object> del(@RequestBody String id) {
        iUserPointsChargrecordService.del(id);
        return AjaxResult.success();
    }

}
