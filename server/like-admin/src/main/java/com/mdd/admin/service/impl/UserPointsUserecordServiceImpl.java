package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdd.admin.service.IUserPointsUserecordService;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.user.UserPointsUserecordCreateValidate;
import com.mdd.admin.validate.user.UserPointsUserecordSearchValidate;
import com.mdd.admin.validate.user.UserPointsUserecordUpdateValidate;
import com.mdd.admin.vo.user.UserPointsUserecordDetailVo;
import com.mdd.admin.vo.user.UserPointsUserecordListedVo;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.user.UserPointsUserecord;
import com.mdd.common.mapper.user.UserPointsUserecordMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 使用记录实现类
 * @author xmg
 */
@Service
public class UserPointsUserecordServiceImpl implements IUserPointsUserecordService {
        
    @Resource
    UserPointsUserecordMapper userPointsUserecordMapper;

    /**
     * 使用记录列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<UserPointsUserecordListedVo>
     */
    @Override
    public PageResult<UserPointsUserecordListedVo> list(PageValidate pageValidate, UserPointsUserecordSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        QueryWrapper<UserPointsUserecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        userPointsUserecordMapper.setSearch(queryWrapper, searchValidate, new String[]{
            "=:userId@user_id:str",
            "=:scene:str",
            "datetime:createTimeStart-createTimeEnd@create_time:str",
        });

        IPage<UserPointsUserecord> iPage = userPointsUserecordMapper.selectPage(new Page<>(page, limit), queryWrapper);

        List<UserPointsUserecordListedVo> list = new LinkedList<>();
        for(UserPointsUserecord item : iPage.getRecords()) {
            UserPointsUserecordListedVo vo = new UserPointsUserecordListedVo();
            BeanUtils.copyProperties(item, vo);
            vo.setCreateTime(item.getCreateTime());
            vo.setUpdateTime(item.getUpdateTime());
            list.add(vo);
        }

        return PageResult.iPageHandle(iPage.getTotal(), iPage.getCurrent(), iPage.getSize(), list);
    }

    /**
     * 使用记录详情
     *
     * @author xmg
     * @param id 主键参数
     * @return UserPointsUserecord
     */
    @Override
    public UserPointsUserecordDetailVo detail(String id) {
        UserPointsUserecord model = userPointsUserecordMapper.selectOne(
                new QueryWrapper<UserPointsUserecord>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在");

        UserPointsUserecordDetailVo vo = new UserPointsUserecordDetailVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    /**
     * 使用记录新增
     *
     * @author xmg
     * @param createValidate 参数
     */
    @Override
    public void add(UserPointsUserecordCreateValidate createValidate) {
        UserPointsUserecord model = new UserPointsUserecord();
        model.setScene(createValidate.getScene());
        model.setConsume(createValidate.getConsume());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        userPointsUserecordMapper.insert(model);
    }

    /**
     * 使用记录编辑
     *
     * @author xmg
     * @param updateValidate 参数
     */
    @Override
    public void edit(UserPointsUserecordUpdateValidate updateValidate) {
        UserPointsUserecord model = userPointsUserecordMapper.selectOne(
                new QueryWrapper<UserPointsUserecord>()
                    .eq("id",  updateValidate.getId())
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        model.setScene(updateValidate.getScene());
        model.setConsume(updateValidate.getConsume());
        model.setUpdateTime(new Date());
        userPointsUserecordMapper.updateById(model);
    }

    /**
     * 使用记录删除
     *
     * @author xmg
     * @param id 主键ID
     */
    @Override
    public void del(String id) {
        UserPointsUserecord model = userPointsUserecordMapper.selectOne(
                new QueryWrapper<UserPointsUserecord>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        userPointsUserecordMapper.delete(new QueryWrapper<UserPointsUserecord>().eq("id", id));
    }

}
