package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdd.admin.service.IUserPointsChargrecordService;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.user.UserPointsChargrecordCreateValidate;
import com.mdd.admin.validate.user.UserPointsChargrecordSearchValidate;
import com.mdd.admin.validate.user.UserPointsChargrecordUpdateValidate;
import com.mdd.admin.vo.user.UserPointsChargrecordDetailVo;
import com.mdd.admin.vo.user.UserPointsChargrecordListedVo;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.user.UserPointsChargrecord;
import com.mdd.common.mapper.user.UserPointsChargrecordMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 充值记录实现类
 * @author xmg
 */
@Service
public class UserPointsChargrecordServiceImpl implements IUserPointsChargrecordService {
        
    @Resource
    UserPointsChargrecordMapper userPointsChargrecordMapper;

    /**
     * 充值记录列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<UserPointsChargrecordListedVo>
     */
    @Override
    public PageResult<UserPointsChargrecordListedVo> list(PageValidate pageValidate, UserPointsChargrecordSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        QueryWrapper<UserPointsChargrecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        userPointsChargrecordMapper.setSearch(queryWrapper, searchValidate, new String[]{
            "=:money:str",
            "=:obtainPoints@obtain_points:str",
            "=:creator:str",
            "datetime:createTimeStart-createTimeEnd@create_time:str",
        });

        IPage<UserPointsChargrecord> iPage = userPointsChargrecordMapper.selectPage(new Page<>(page, limit), queryWrapper);

        List<UserPointsChargrecordListedVo> list = new LinkedList<>();
        for(UserPointsChargrecord item : iPage.getRecords()) {
            UserPointsChargrecordListedVo vo = new UserPointsChargrecordListedVo();
            BeanUtils.copyProperties(item, vo);
            vo.setCreateTime(item.getCreateTime());
            vo.setUpdateTime(item.getUpdateTime());
            list.add(vo);
        }

        return PageResult.iPageHandle(iPage.getTotal(), iPage.getCurrent(), iPage.getSize(), list);
    }

    /**
     * 充值记录详情
     *
     * @author xmg
     * @param id 主键参数
     * @return UserPointsChargrecord
     */
    @Override
    public UserPointsChargrecordDetailVo detail(String id) {
        UserPointsChargrecord model = userPointsChargrecordMapper.selectOne(
                new QueryWrapper<UserPointsChargrecord>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在");

        UserPointsChargrecordDetailVo vo = new UserPointsChargrecordDetailVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    /**
     * 充值记录新增
     *
     * @author xmg
     * @param createValidate 参数
     */
    @Override
    public void add(UserPointsChargrecordCreateValidate createValidate) {
        UserPointsChargrecord model = new UserPointsChargrecord();
        model.setMoney(createValidate.getMoney());
        model.setObtainPoints(createValidate.getObtainPoints());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        userPointsChargrecordMapper.insert(model);
    }

    /**
     * 充值记录编辑
     *
     * @author xmg
     * @param updateValidate 参数
     */
    @Override
    public void edit(UserPointsChargrecordUpdateValidate updateValidate) {
        UserPointsChargrecord model = userPointsChargrecordMapper.selectOne(
                new QueryWrapper<UserPointsChargrecord>()
                    .eq("id",  updateValidate.getId())
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        model.setId(updateValidate.getId());
        model.setMoney(updateValidate.getMoney());
        model.setObtainPoints(updateValidate.getObtainPoints());
        model.setUpdateTime(new Date());
        userPointsChargrecordMapper.updateById(model);
    }

    /**
     * 充值记录删除
     *
     * @author xmg
     * @param id 主键ID
     */
    @Override
    public void del(String id) {
        UserPointsChargrecord model = userPointsChargrecordMapper.selectOne(
                new QueryWrapper<UserPointsChargrecord>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        userPointsChargrecordMapper.delete(new QueryWrapper<UserPointsChargrecord>().eq("id", id));
    }

}
