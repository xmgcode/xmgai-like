package com.mdd.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.user.UserPointsCreateValidate;
import com.mdd.admin.validate.user.UserPointsSearchValidate;
import com.mdd.admin.validate.user.UserPointsUpdateValidate;
import com.mdd.admin.vo.user.UserPointsDetailVo;
import com.mdd.admin.vo.user.UserPointsListedVo;
import com.mdd.common.core.PageResult;

/**
 * 用户点数服务接口类
 * @author xmg
 */
public interface IUserPointsService {

    /**
     * 用户点数列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<UserPointsListedVo>
     */
    PageResult<UserPointsListedVo> list(PageValidate pageValidate, UserPointsSearchValidate searchValidate);

    /**
     * 用户点数详情
     *
     * @author xmg
     * @param id 主键ID
     * @return UserPointsDetailVo
     */
    UserPointsDetailVo detail(String id);

    /**
     * 用户点数新增
     *
     * @author xmg
     * @param createValidate 参数
     */
    void add(UserPointsCreateValidate createValidate);

    /**
     * 用户点数编辑
     *
     * @author xmg
     * @param updateValidate 参数
     */
    void edit(UserPointsUpdateValidate updateValidate);

    /**
     * 用户点数删除
     *
     * @author xmg
     * @param id 主键ID
     */
    void del(String id);


    /**
     * 校验点数是否够用
     * @param userId
     * @return
     */
    JSONObject inspectPoints(String userId);

}
