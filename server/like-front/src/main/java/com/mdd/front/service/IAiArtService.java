package com.mdd.front.service;

import com.mdd.common.core.PageResult;
import com.mdd.common.entity.ai.AiArtcodeModel;
import com.mdd.front.validate.common.PageValidate;
import com.mdd.front.vo.ai.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 文章服务接口类
 */
public interface IAiArtService {

    /**
     * 查询生成的二维码
     * @param imageId
     */
    AiArtcodeRecordVo getQrCode(String imageId);


    /**
     * 查询点数
     * @param userId
     */
    BigDecimal selectPoints(String userId);


    /**
     * 查询点数
     * @param userId
     */
    List<UserPointsChargrecordVo> selectUserRecords(String userId);


    /**
     * 查询绘画记录
     * @Author xmg
     * @param userId
     * @return
     */
    List<AiArtcodeRecordVo> selectArtCodeRecords(String userId,String aiType);



    /**
     * 查询点数消耗记录
     * @Author xmg
     * @param userId
     * @return
     */
    List<UserPointsRecordVo> selectPointsConsumred(String userId);


    /**
     * 查询所有数据模型
     * @Author xmg
     * @return
     */
    List<AiArtcodeOwnModelVo> selectAllModel();


    /**
     * AI列表
     *
     * @author xmg
     * @param pageValidate 分页参数
     * @return PageResult<AiRecordListedVo>
     */
    PageResult<AiRecordListedVo> list(PageValidate pageValidate);

}
