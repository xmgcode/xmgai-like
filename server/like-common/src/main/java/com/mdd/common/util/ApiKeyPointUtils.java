package com.mdd.common.util;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdd.common.cache.ConfigCache;
import com.mdd.common.entity.user.User;
import com.mdd.common.entity.user.UserPoints;
import com.mdd.common.entity.user.UserPointsUserecord;
import com.mdd.common.mapper.user.UserMapper;
import com.mdd.common.mapper.user.UserPointsMapper;
import com.mdd.common.mapper.user.UserPointsUserecordMapper;
import com.mdd.common.vo.ai.AiApiKeyVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @Author xmg
 * 金额处理工具
 */
@Component
public class ApiKeyPointUtils {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserPointsMapper userPointsMapper;

    @Autowired
    private UserPointsUserecordMapper userPointsUserecordMapper;
    /**
     * 校验积分
     * @param
     * @return
     */
    public AiApiKeyVo validateApiKey(String userId, String aiConfigType, String aiConfigName){
//        //校验apikey
//        AiApiKeyVo aiApiKeyVo = new AiApiKeyVo();
//        if(StringUtils.isEmpty(apiKey)){
//            aiApiKeyVo.setFlag(false);
//            aiApiKeyVo.setMsg("apiKey参数缺失!");
//            return aiApiKeyVo;
//        }
//        User user = userMapper.selectOne(new QueryWrapper<User>()
//                .eq("api_key", apiKey)
//                .eq("is_delete", 0)
//                .last("limit 1"));
//        if(null == user){
//            aiApiKeyVo.setFlag(false);
//            aiApiKeyVo.setMsg("apiKey参数错误!");
//            return aiApiKeyVo;
//        }
//        String userKey = user.getApiKey();
//        if(!apiKey.equals(userKey)){
//            aiApiKeyVo.setFlag(false);
//            aiApiKeyVo.setMsg("apiKey参数错误!");
//            return aiApiKeyVo;
//        }
        //校验积分

        //从缓存中获取相应的点数扣除配置
        AiApiKeyVo aiApiKeyVo = new AiApiKeyVo();
        Map<String, Object> configMap = ConfigCache.getAiConfigCache();
        String configStr = configMap.get(aiConfigType).toString();
        JSONObject obj = JSONObject.parseObject(configStr);
        String perPoints = obj.get(aiConfigName).toString();
        //每次消耗的点数
        BigDecimal pointsDecimal = new BigDecimal(perPoints);
        UserPoints userPoints = userPointsMapper.selectOne(
                new QueryWrapper<UserPoints>()
                        .eq("user_id", String.valueOf(userId))
                        .last("limit 1"));
        if (null == userPoints){
            aiApiKeyVo.setFlag(false);
            aiApiKeyVo.setMsg("请先充值点数!");
            return aiApiKeyVo;
        }
        if(pointsDecimal.compareTo(userPoints.getRemainPoints()) > 0) {
            aiApiKeyVo.setFlag(false);
            aiApiKeyVo.setMsg("点数不足，请充值！");
            return aiApiKeyVo;
        }
        aiApiKeyVo.setMsg("检验正常");
        aiApiKeyVo.setUserId(userId);
        aiApiKeyVo.setFlag(true);
        return aiApiKeyVo;

    }


    /**
     * 扣除积分
     * @param userId
     * @return
     */
    public void lessPoints(String userId,String scene,String aiConfigType,String aiConfigName){
        //从缓存中获取相应的点数扣除配置
        Map<String, Object> configMap = ConfigCache.getAiConfigCache();
        String configStr = configMap.get(aiConfigType).toString();
        JSONObject obj = JSONObject.parseObject(configStr);
        String perPoints = obj.get(aiConfigName).toString();
        //每次消耗的点数
        BigDecimal pointsDecimal = new BigDecimal(perPoints);

        UserPoints userPoints = userPointsMapper.selectOne(
                new QueryWrapper<UserPoints>()
                        .eq("user_id", userId)
                        .last("limit 1"));
        // 点数转bigdecimal，更新点数
        BigDecimal remainPoints = userPoints.getRemainPoints();
        //减去每次请求所需的点数
        userPoints.setRemainPoints(remainPoints.subtract(pointsDecimal));
        userPointsMapper.updateById(userPoints);
        //新增点数消耗记录
        UserPointsUserecord userPointsUserecord = new UserPointsUserecord();
        userPointsUserecord.setId(SnowIdUtil.getStrUUid());
        userPointsUserecord.setUserId(userId);
        userPointsUserecord.setScene(scene);
        userPointsUserecord.setConsume(pointsDecimal);
        userPointsUserecord.setCreateTime(new Date());
        userPointsUserecordMapper.insert(userPointsUserecord);
    }


}
