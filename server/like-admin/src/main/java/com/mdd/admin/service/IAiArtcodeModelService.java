package com.mdd.admin.service;

import com.mdd.admin.validate.ai.model.AiArtcodeModelCreateValidate;
import com.mdd.admin.validate.ai.model.AiArtcodeModelSearchValidate;
import com.mdd.admin.validate.ai.model.AiArtcodeModelUpdateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.ai.model.AiArtcodeModelDetailVo;
import com.mdd.admin.vo.ai.model.AiArtcodeModelListedVo;
import com.mdd.common.core.PageResult;

/**
 * 艺术二维码模板服务接口类
 * @author xmg
 */
public interface IAiArtcodeModelService {

    /**
     * 艺术二维码模板列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<AiArtcodeModelListedVo>
     */
    PageResult<AiArtcodeModelListedVo> list(PageValidate pageValidate, AiArtcodeModelSearchValidate searchValidate);

    /**
     * 艺术二维码模板详情
     *
     * @author xmg
     * @param id 主键ID
     * @return AiArtcodeModelDetailVo
     */
    AiArtcodeModelDetailVo detail(String id);

    /**
     * 艺术二维码模板新增
     *
     * @author xmg
     * @param createValidate 参数
     */
    void add(AiArtcodeModelCreateValidate createValidate);

    /**
     * 艺术二维码模板编辑
     *
     * @author xmg
     * @param updateValidate 参数
     */
    void edit(AiArtcodeModelUpdateValidate updateValidate);

    /**
     * 艺术二维码模板删除
     *
     * @author xmg
     * @param id 主键ID
     */
    void del(String id);







}
