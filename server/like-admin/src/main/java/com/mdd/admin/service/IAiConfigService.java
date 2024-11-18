package com.mdd.admin.service;

import com.mdd.admin.validate.ai.aiconfig.AiConfigCreateValidate;
import com.mdd.admin.validate.ai.aiconfig.AiConfigSearchValidate;
import com.mdd.admin.validate.ai.aiconfig.AiConfigUpdateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.ai.aiconfig.AiConfigDetailVo;
import com.mdd.admin.vo.ai.aiconfig.AiConfigListedVo;
import com.mdd.common.core.PageResult;

/**
 * AI配置服务接口类
 * @author xmg
 */
public interface IAiConfigService {

    /**
     * AI配置列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<AiConfigListedVo>
     */
    PageResult<AiConfigListedVo> list(PageValidate pageValidate, AiConfigSearchValidate searchValidate);

    /**
     * AI配置详情
     *
     * @author xmg
     * @param id 主键ID
     * @return AiConfigDetailVo
     */
    AiConfigDetailVo detail(String id);

    /**
     * AI配置新增
     *
     * @author xmg
     * @param createValidate 参数
     */
    void add(AiConfigCreateValidate createValidate);

    /**
     * AI配置编辑
     *
     * @author xmg
     * @param updateValidate 参数
     */
    void edit(AiConfigUpdateValidate updateValidate);

    /**
     * AI配置删除
     *
     * @author xmg
     * @param id 主键ID
     */
    void del(String id);


    /**
     * 根据name查询配置
     *
     * @author xmg
     * @param name
     * @return AiConfigDetailVo
     */
    AiConfigDetailVo getByName(String name);

}
