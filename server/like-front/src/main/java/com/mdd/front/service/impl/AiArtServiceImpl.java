package com.mdd.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.ai.AiArtcode;
import com.mdd.common.entity.ai.AiArtcodeModel;
import com.mdd.common.entity.user.UserPoints;
import com.mdd.common.entity.user.UserPointsChargrecord;
import com.mdd.common.entity.user.UserPointsUserecord;
import com.mdd.common.mapper.ai.AiArtcodeMapper;
import com.mdd.common.mapper.ai.AiArtcodeModelMapper;
import com.mdd.common.mapper.user.UserPointsChargrecordMapper;
import com.mdd.common.mapper.user.UserPointsMapper;
import com.mdd.common.mapper.user.UserPointsUserecordMapper;
import com.mdd.front.service.IAiArtService;
import com.mdd.front.validate.common.PageValidate;
import com.mdd.front.vo.ai.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 文章服务实现类
 */
@Service
public class AiArtServiceImpl implements IAiArtService {


    @Resource
    private UserPointsMapper userPointsMapper;

    @Resource
    private UserPointsChargrecordMapper userPointsChargrecordMapper;

    @Resource
    private AiArtcodeMapper aiArtcodeMapper;

    @Resource
    private UserPointsUserecordMapper userPointsUserecordMapper;

    @Resource
    private AiArtcodeModelMapper aiArtcodeModelMapper;

    @Override
    public AiArtcodeRecordVo getQrCode(String imageId) {
        AiArtcode model = aiArtcodeMapper.selectOne(
                new QueryWrapper<AiArtcode>()
                        .eq("img_uuid", imageId)
                        .last("limit 1"));
        if(null == model){
            return null;
        }
        AiArtcodeRecordVo vo = new AiArtcodeRecordVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    @Override
    public BigDecimal selectPoints(String userId) {
        UserPoints userPoints = userPointsMapper.selectOne(
                new QueryWrapper<UserPoints>()
                        .eq("user_id", String.valueOf(userId))
                        .last("limit 1"));
        if(null == userPoints){
            BigDecimal zero = BigDecimal.ZERO;
            return zero;
        }
        BigDecimal remainPoints = userPoints.getRemainPoints();
        return remainPoints;
    }

    @Override
    public List<UserPointsChargrecordVo> selectUserRecords(String userId) {
        List<UserPointsChargrecord> chargrecords = userPointsChargrecordMapper.selectList(
                new QueryWrapper<UserPointsChargrecord>()
                        .select("money", "obtain_points","give_points","create_time")
                        .eq("user_id", userId)
                        .eq("deleted", 0));
        List<UserPointsChargrecordVo> list = new LinkedList<>();
        for(UserPointsChargrecord item : chargrecords) {
            UserPointsChargrecordVo vos = new UserPointsChargrecordVo();
            BeanUtils.copyProperties(item, vos);
            list.add(vos);
        }
        return list;
    }



    @Override
    public List<AiArtcodeRecordVo> selectArtCodeRecords(String userId,String aiType) {
        List<AiArtcode> aiArtcodes = aiArtcodeMapper.selectList(new QueryWrapper<AiArtcode>()
                .select("id", "local_urls","prompt","revised_prompt","create_time")
                .eq("user_id", userId)
                .eq("deleted", 0)
                .orderByDesc("create_time"));
        List<AiArtcodeRecordVo> list = new LinkedList<>();
        for(AiArtcode item : aiArtcodes) {
            AiArtcodeRecordVo vos = new AiArtcodeRecordVo();
            BeanUtils.copyProperties(item, vos);
            list.add(vos);
        }
        return list;
    }


    @Override
    public List<UserPointsRecordVo> selectPointsConsumred(String userId) {
        List<UserPointsUserecord> aiArtcodes = userPointsUserecordMapper.selectList(new QueryWrapper<UserPointsUserecord>()
                .select("id","scene","consume","create_time")
                .eq("user_id", userId)
                .eq("deleted", 0)
                .orderByDesc("create_time"));
        List<UserPointsRecordVo> list = new LinkedList<>();
        for(UserPointsUserecord item : aiArtcodes) {
            UserPointsRecordVo vos = new UserPointsRecordVo();
            BeanUtils.copyProperties(item, vos);
            list.add(vos);
        }
        return list;
    }

    @Override
    public List<AiArtcodeOwnModelVo> selectAllModel() {
        List<AiArtcodeModel> models = aiArtcodeModelMapper.selectList(
                new QueryWrapper<AiArtcodeModel>()
                        .select("id", "model_num", "name","preview_img")
                        .eq("deleted", 0));
        List<AiArtcodeOwnModelVo> lists = new LinkedList<>();
        for (AiArtcodeModel model : models) {
            AiArtcodeOwnModelVo vo = new AiArtcodeOwnModelVo();
            BeanUtils.copyProperties(model, vo);
            lists.add(vo);
        }
        return lists;
    }

    @Override
    public PageResult<AiRecordListedVo> list(PageValidate pageValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        QueryWrapper<AiArtcode> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");

        AiRecordSearchValidate searchValidate = new AiRecordSearchValidate();
        searchValidate.setUserId(pageValidate.getUserId());
        aiArtcodeMapper.setSearch(queryWrapper, searchValidate, new String[]{
                "=:userId@user_id:str",
        });

        IPage<AiArtcode> iPage = aiArtcodeMapper.selectPage(new Page<>(page, limit), queryWrapper);

        List<AiRecordListedVo> list = new LinkedList<>();
        for(AiArtcode item : iPage.getRecords()) {
            AiRecordListedVo vo = new AiRecordListedVo();
            BeanUtils.copyProperties(item, vo);
            vo.setCreateTime(item.getCreateTime());
            list.add(vo);
        }

        return PageResult.iPageHandle(iPage.getTotal(), iPage.getCurrent(), iPage.getSize(), list);
    }


}
