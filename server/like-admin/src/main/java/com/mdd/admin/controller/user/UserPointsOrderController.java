package com.mdd.admin.controller.user;

import com.mdd.admin.aop.Log;
import com.mdd.admin.service.IUserPointsOrderService;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.user.UserPointsOrderCreateValidate;
import com.mdd.admin.validate.user.UserPointsOrderSearchValidate;
import com.mdd.admin.validate.user.UserPointsOrderUpdateValidate;
import com.mdd.admin.vo.user.UserPointsOrderDetailVo;
import com.mdd.admin.vo.user.UserPointsOrderListedVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/order")
@Api(tags = "点数订单管理")
public class UserPointsOrderController {

    @Resource
    IUserPointsOrderService iUserPointsOrderService;

    @GetMapping("/list")
    @ApiOperation(value="点数订单列表")
    public AjaxResult<PageResult<UserPointsOrderListedVo>> list(@Validated PageValidate pageValidate,
                                                                @Validated UserPointsOrderSearchValidate searchValidate) {
        PageResult<UserPointsOrderListedVo> list = iUserPointsOrderService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    @ApiOperation(value="点数订单详情")
    public AjaxResult<UserPointsOrderDetailVo> detail(@Validated @IDMust() @RequestParam("id") String id) {
        UserPointsOrderDetailVo detail = iUserPointsOrderService.detail(id);
        return AjaxResult.success(detail);
    }

    @Log(title = "点数订单新增")
    @PostMapping("/add")
    @ApiOperation(value="点数订单新增")
    public AjaxResult<Object> add(@Validated @RequestBody UserPointsOrderCreateValidate createValidate) {
        iUserPointsOrderService.add(createValidate);
        return AjaxResult.success();
    }

    @Log(title = "点数订单编辑")
    @PostMapping("/edit")
    @ApiOperation(value="点数订单编辑")
    public AjaxResult<Object> edit(@Validated @RequestBody UserPointsOrderUpdateValidate updateValidate) {
        iUserPointsOrderService.edit(updateValidate);
        return AjaxResult.success();
    }

    @Log(title = "点数订单删除")
    @PostMapping("/del")
    @ApiOperation(value="点数订单删除")
    public AjaxResult<Object> del(@RequestBody String id) {
        iUserPointsOrderService.del(id);
        return AjaxResult.success();
    }

}
