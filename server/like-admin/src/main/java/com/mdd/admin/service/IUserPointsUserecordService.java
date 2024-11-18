package com.mdd.admin.service;

import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.user.UserPointsUserecordCreateValidate;
import com.mdd.admin.validate.user.UserPointsUserecordSearchValidate;
import com.mdd.admin.validate.user.UserPointsUserecordUpdateValidate;
import com.mdd.admin.vo.user.UserPointsUserecordDetailVo;
import com.mdd.admin.vo.user.UserPointsUserecordListedVo;
import com.mdd.common.core.PageResult;

/**
 * 使用记录服务接口类
 * @author xmg
 */
public interface IUserPointsUserecordService {

    /**
     * 使用记录列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<UserPointsUserecordListedVo>
     */
    PageResult<UserPointsUserecordListedVo> list(PageValidate pageValidate, UserPointsUserecordSearchValidate searchValidate);

    /**
     * 使用记录详情
     *
     * @author xmg
     * @param id 主键ID
     * @return UserPointsUserecordDetailVo
     */
    UserPointsUserecordDetailVo detail(String id);

    /**
     * 使用记录新增
     *
     * @author xmg
     * @param createValidate 参数
     */
    void add(UserPointsUserecordCreateValidate createValidate);

    /**
     * 使用记录编辑
     *
     * @author xmg
     * @param updateValidate 参数
     */
    void edit(UserPointsUserecordUpdateValidate updateValidate);

    /**
     * 使用记录删除
     *
     * @author xmg
     * @param id 主键ID
     */
    void del(String id);

}
