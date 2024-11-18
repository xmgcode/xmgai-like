package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdd.admin.service.IUserPointsOrderService;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.user.UserPointsOrderCreateValidate;
import com.mdd.admin.validate.user.UserPointsOrderSearchValidate;
import com.mdd.admin.validate.user.UserPointsOrderUpdateValidate;
import com.mdd.admin.vo.user.UserPointsOrderDetailVo;
import com.mdd.admin.vo.user.UserPointsOrderListedVo;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.user.UserPointsOrder;
import com.mdd.common.mapper.user.UserPointsOrderMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 点数订单实现类
 * @author xmg
 */
@Service
public class UserPointsOrderServiceImpl implements IUserPointsOrderService {
        
    @Resource
    UserPointsOrderMapper userPointsOrderMapper;

    /**
     * 点数订单列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<UserPointsOrderListedVo>
     */
    @Override
    public PageResult<UserPointsOrderListedVo> list(PageValidate pageValidate, UserPointsOrderSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        QueryWrapper<UserPointsOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        userPointsOrderMapper.setSearch(queryWrapper, searchValidate, new String[]{
            "=:money:str",
            "=:payNo@pay_no:str",
            "=:payStatus@pay_status:str",
            "datetime:createTimeStart-createTimeEnd@create_time:str",
        });

        IPage<UserPointsOrder> iPage = userPointsOrderMapper.selectPage(new Page<>(page, limit), queryWrapper);

        List<UserPointsOrderListedVo> list = new LinkedList<>();
        for(UserPointsOrder item : iPage.getRecords()) {
            UserPointsOrderListedVo vo = new UserPointsOrderListedVo();
            BeanUtils.copyProperties(item, vo);
            vo.setCreateTime(item.getCreateTime());
            list.add(vo);
        }

        return PageResult.iPageHandle(iPage.getTotal(), iPage.getCurrent(), iPage.getSize(), list);
    }

    /**
     * 点数订单详情
     *
     * @author xmg
     * @param id 主键参数
     * @return UserPointsOrder
     */
    @Override
    public UserPointsOrderDetailVo detail(String id) {
        UserPointsOrder model = userPointsOrderMapper.selectOne(
                new QueryWrapper<UserPointsOrder>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在");

        UserPointsOrderDetailVo vo = new UserPointsOrderDetailVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    /**
     * 点数订单新增
     *
     * @author xmg
     * @param createValidate 参数
     */
    @Override
    public void add(UserPointsOrderCreateValidate createValidate) {
        UserPointsOrder model = new UserPointsOrder();
        model.setMoney(createValidate.getMoney());
        model.setPayNo(createValidate.getPayNo());
        model.setPayStatus(createValidate.getPayStatus());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        userPointsOrderMapper.insert(model);
    }

    /**
     * 点数订单编辑
     *
     * @author xmg
     * @param updateValidate 参数
     */
    @Override
    public void edit(UserPointsOrderUpdateValidate updateValidate) {
        UserPointsOrder model = userPointsOrderMapper.selectOne(
                new QueryWrapper<UserPointsOrder>()
                    .eq("id",  updateValidate.getId())
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        model.setMoney(updateValidate.getMoney());
        model.setPayNo(updateValidate.getPayNo());
        model.setPayStatus(updateValidate.getPayStatus());
        model.setUpdateTime(new Date());
        userPointsOrderMapper.updateById(model);
    }

    /**
     * 点数订单删除
     *
     * @author xmg
     * @param id 主键ID
     */
    @Override
    public void del(String id) {
        UserPointsOrder model = userPointsOrderMapper.selectOne(
                new QueryWrapper<UserPointsOrder>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        userPointsOrderMapper.delete(new QueryWrapper<UserPointsOrder>().eq("id", id));
    }

}
