package com.mdd.admin.service;

import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.user.UserPointsOrderCreateValidate;
import com.mdd.admin.validate.user.UserPointsOrderSearchValidate;
import com.mdd.admin.validate.user.UserPointsOrderUpdateValidate;
import com.mdd.admin.vo.user.UserPointsOrderDetailVo;
import com.mdd.admin.vo.user.UserPointsOrderListedVo;
import com.mdd.common.core.PageResult;

/**
 * 点数订单服务接口类
 * @author xmg
 */
public interface IUserPointsOrderService {

    /**
     * 点数订单列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<UserPointsOrderListedVo>
     */
    PageResult<UserPointsOrderListedVo> list(PageValidate pageValidate, UserPointsOrderSearchValidate searchValidate);

    /**
     * 点数订单详情
     *
     * @author xmg
     * @param id 主键ID
     * @return UserPointsOrderDetailVo
     */
    UserPointsOrderDetailVo detail(String id);

    /**
     * 点数订单新增
     *
     * @author xmg
     * @param createValidate 参数
     */
    void add(UserPointsOrderCreateValidate createValidate);

    /**
     * 点数订单编辑
     *
     * @author xmg
     * @param updateValidate 参数
     */
    void edit(UserPointsOrderUpdateValidate updateValidate);

    /**
     * 点数订单删除
     *
     * @author xmg
     * @param id 主键ID
     */
    void del(String id);

}
