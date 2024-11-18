package com.mdd.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.mdd.admin.validate.ai.artcode.AiArtcodeCreateValidate;
import com.mdd.admin.validate.ai.artcode.AiArtcodeSearchValidate;
import com.mdd.admin.validate.ai.artcode.AiArtcodeUpdateValidate;
import com.mdd.admin.validate.ai.artcode.AiQrModelCreateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.ai.artcode.AiArtcodeDetailVo;
import com.mdd.admin.vo.ai.artcode.AiArtcodeListedVo;
import com.mdd.common.core.PageResult;

import javax.servlet.http.HttpServletRequest;

/**
 * 二维码生成服务接口类
 * @author xmg
 */
public interface IAiArtcodeService {

    /**
     * 二维码生成列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<AiArtcodeListedVo>
     */
    PageResult<AiArtcodeListedVo> list(PageValidate pageValidate, AiArtcodeSearchValidate searchValidate);

    /**
     * 二维码生成详情
     *
     * @author xmg
     * @param id 主键ID
     * @return AiArtcodeDetailVo
     */
    AiArtcodeDetailVo detail(String id);

    /**
     * 二维码生成新增
     *
     * @author xmg
     * @param createValidate 参数
     */
    void add(AiArtcodeCreateValidate createValidate);

    /**
     * 二维码生成编辑
     *
     * @author xmg
     * @param updateValidate 参数
     */
    void edit(AiArtcodeUpdateValidate updateValidate);

    /**
     * 二维码生成删除
     *
     * @author xmg
     * @param id 主键ID
     */
    void del(String id);

    /**
     * 生成艺术二维码
     * @Author xmg
     */
    JSONObject createQrCode(AiQrModelCreateValidate createCodeValidate, HttpServletRequest request);


    /**
     * 查询艺术二维码
     * @Author xmg
     */
    String getQrCode(String imageId);

}
