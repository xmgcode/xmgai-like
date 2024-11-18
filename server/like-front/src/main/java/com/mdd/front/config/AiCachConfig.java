package com.mdd.front.config;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdd.common.entity.ai.AiConfig;
import com.mdd.common.mapper.ai.AiConfigMapper;
import com.mdd.common.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * AI配置
 */
@Component
public class AiCachConfig {


    private final String cacheKey = "AiConfig";

    private final RedisUtils redisUtils;

    @Autowired
    public AiCachConfig(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }


    @Autowired
    private AiConfigMapper aiConfigMapper;

    /**
     * 初始化AI配置到redis中
     */
    @PostConstruct
    public void loadAiCache() {
        // 查询全部配置项，设置缓存
        List<AiConfig> configs = aiConfigMapper.selectList(
                new QueryWrapper<AiConfig>()
                        .select("id", "type", "name", "value"));

        List<String> typeList = configs.stream().map(AiConfig::getType)
                .collect(Collectors.toList())
                .stream().distinct()
                .collect(Collectors.toList());

        Map<String, Object> map = new LinkedHashMap<>();
        Map<String, Object> subMap = new LinkedHashMap<>();
        for (String typeItem : typeList) {
            subMap.clear();
            for (AiConfig configItem : configs) {
                if (configItem.getType().equals(typeItem)) {
                    subMap.put(configItem.getName(), configItem.getValue());
                }
            }
            map.put(typeItem, JSON.toJSONString(subMap));
        }
        RedisUtils.set(cacheKey, JSON.toJSONString(map));

    }


}
