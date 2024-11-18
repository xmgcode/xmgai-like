package com.mdd.admin.service;

import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.user.UserPointsChargrecordCreateValidate;
import com.mdd.admin.validate.user.UserPointsChargrecordSearchValidate;
import com.mdd.admin.validate.user.UserPointsChargrecordUpdateValidate;
import com.mdd.admin.vo.user.UserPointsChargrecordDetailVo;
import com.mdd.admin.vo.user.UserPointsChargrecordListedVo;
import com.mdd.common.core.PageResult;

/**
 * 充值记录服务接口类
 * @author xmg
 */
public interface IUserPointsChargrecordService {

    /**
     * 充值记录列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<UserPointsChargrecordListedVo>
     */
    PageResult<UserPointsChargrecordListedVo> list(PageValidate pageValidate, UserPointsChargrecordSearchValidate searchValidate);

    /**
     * 充值记录详情
     *
     * @author xmg
     * @param id 主键ID
     * @return UserPointsChargrecordDetailVo
     */
    UserPointsChargrecordDetailVo detail(String id);

    /**
     * 充值记录新增
     *
     * @author xmg
     * @param createValidate 参数
     */
    void add(UserPointsChargrecordCreateValidate createValidate);

    /**
     * 充值记录编辑
     *
     * @author xmg
     * @param updateValidate 参数
     */
    void edit(UserPointsChargrecordUpdateValidate updateValidate);

    /**
     * 充值记录删除
     *
     * @author xmg
     * @param id 主键ID
     */
    void del(String id);

}
