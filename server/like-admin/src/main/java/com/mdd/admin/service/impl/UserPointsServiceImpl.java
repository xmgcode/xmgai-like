package com.mdd.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdd.admin.service.IUserPointsService;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.user.UserPointsCreateValidate;
import com.mdd.admin.validate.user.UserPointsSearchValidate;
import com.mdd.admin.validate.user.UserPointsUpdateValidate;
import com.mdd.admin.vo.user.UserPointsDetailVo;
import com.mdd.admin.vo.user.UserPointsListedVo;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.user.UserPoints;
import com.mdd.common.mapper.user.UserPointsMapper;
import com.mdd.common.util.YmlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 用户点数实现类
 * @author xmg
 */
@Service
public class UserPointsServiceImpl implements IUserPointsService {
        
    @Resource
    UserPointsMapper userPointsMapper;

    /**
     * 用户点数列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<UserPointsListedVo>
     */
    @Override
    public PageResult<UserPointsListedVo> list(PageValidate pageValidate, UserPointsSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        QueryWrapper<UserPoints> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        userPointsMapper.setSearch(queryWrapper, searchValidate, new String[]{
            "=:userId@user_id:str",
            "datetime:createTimeStart-createTimeEnd@create_time:str",
            "datetime:updateTimeStart-updateTimeEnd@update_time:str",
        });

        IPage<UserPoints> iPage = userPointsMapper.selectPage(new Page<>(page, limit), queryWrapper);

        List<UserPointsListedVo> list = new LinkedList<>();
        for(UserPoints item : iPage.getRecords()) {
            UserPointsListedVo vo = new UserPointsListedVo();
            BeanUtils.copyProperties(item, vo);
            vo.setCreateTime(item.getCreateTime());
            vo.setUpdateTime(item.getUpdateTime());
            list.add(vo);
        }

        return PageResult.iPageHandle(iPage.getTotal(), iPage.getCurrent(), iPage.getSize(), list);
    }

    /**
     * 用户点数详情
     *
     * @author xmg
     * @param id 主键参数
     * @return UserPoints
     */
    @Override
    public UserPointsDetailVo detail(String id) {
        UserPoints model = userPointsMapper.selectOne(
                new QueryWrapper<UserPoints>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在");

        UserPointsDetailVo vo = new UserPointsDetailVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    /**
     * 用户点数新增
     *
     * @author xmg
     * @param createValidate 参数
     */
    @Override
    public void add(UserPointsCreateValidate createValidate) {
        UserPoints model = new UserPoints();
        model.setTotalPoints(createValidate.getTotalPoints());
        model.setRemainPoints(createValidate.getRemainPoints());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        userPointsMapper.insert(model);
    }

    /**
     * 用户点数编辑
     *
     * @author xmg
     * @param updateValidate 参数
     */
    @Override
    public void edit(UserPointsUpdateValidate updateValidate) {
        UserPoints model = userPointsMapper.selectOne(
                new QueryWrapper<UserPoints>()
                    .eq("id",  updateValidate.getId())
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        model.setId(updateValidate.getId());
        model.setTotalPoints(updateValidate.getTotalPoints());
        model.setRemainPoints(updateValidate.getRemainPoints());
        model.setUpdateTime(new Date());
        userPointsMapper.updateById(model);
    }

    /**
     * 用户点数删除
     *
     * @author xmg
     * @param id 主键ID
     */
    @Override
    public void del(String id) {
        UserPoints model = userPointsMapper.selectOne(
                new QueryWrapper<UserPoints>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        userPointsMapper.delete(new QueryWrapper<UserPoints>().eq("id", id));
    }

    @Override
    public JSONObject inspectPoints(String userId) {
        JSONObject reJson = new JSONObject();
        String resEachPoints = YmlUtils.get("ai.artcode.eachPoints");
        BigDecimal pointsDecimal = new BigDecimal(resEachPoints);
        UserPoints userPoints = userPointsMapper.selectOne(
                new QueryWrapper<UserPoints>()
                        .eq("user_id", String.valueOf(userId))
                        .last("limit 1"));
        if (null == userPoints){
            reJson.put("code","201");
            reJson.put("msg","请先充值点数!");
        }else if(pointsDecimal.compareTo(userPoints.getRemainPoints()) > 0) {
            reJson.put("code", "201");
            reJson.put("msg", "点数不足，请充值！");
        }else{
            reJson.put("code","200");
            reJson.put("msg","点数正常！");
        }
        return reJson;
    }

}
