package com.mdd.admin.service.impl;

import com.mdd.admin.service.ISettingWebsiteService;
import com.mdd.admin.validate.setting.SettingWebsiteValidate;
import com.mdd.admin.vo.setting.SettingWebsiteVo;
import com.mdd.common.util.ConfigUtils;
import com.mdd.common.util.UrlUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 网站信息配置服务实现类
 */
@Service
public class SettingWebsiteServiceImpl implements ISettingWebsiteService {

    /**
     * 获取网站信息
     *
     * @author fzr
     * @return Map<String, String>
     */
    @Override
    public SettingWebsiteVo detail() {
        Map<String, String> config = ConfigUtils.get("website");

        SettingWebsiteVo vo = new SettingWebsiteVo();
        vo.setName(config.getOrDefault("name", ""));
        vo.setLogo(config.getOrDefault("logo", ""));
        vo.setFavicon(config.getOrDefault("favicon", ""));
        vo.setBackdrop(config.getOrDefault("backdrop", ""));

        vo.setShopName(config.getOrDefault("shopName", ""));
        vo.setShopLogo(config.getOrDefault("shopLogo", ""));

        vo.setPcLogo(config.getOrDefault("pcLogo", ""));
        vo.setPcIco(config.getOrDefault("pcIco", ""));
        vo.setPcTitle(config.getOrDefault("pcTitle", ""));
        vo.setPcDesc(config.getOrDefault("pcDesc", ""));
        vo.setPcKeywords(config.getOrDefault("pcKeywords", ""));
        return vo;
    }

    /**
     * 保存网站信息
     *
     * @author fzr
     * @param websiteValidate 参数
     */
    @Override
    public void save(SettingWebsiteValidate websiteValidate) {
        ConfigUtils.set("website", "name", websiteValidate.getName());
        String path = websiteValidate.getLogo();
        String newPath = UrlUtils.toRelativeUrl(websiteValidate.getLogo());
        ConfigUtils.set("website", "logo", UrlUtils.toRelativeUrl(websiteValidate.getLogo()));
        ConfigUtils.set("website", "favicon", UrlUtils.toRelativeUrl(websiteValidate.getFavicon()));
        ConfigUtils.set("website", "backdrop", UrlUtils.toRelativeUrl(websiteValidate.getBackdrop()));

        ConfigUtils.set("website", "shopName", websiteValidate.getShopName());
        ConfigUtils.set("website", "shopLogo", UrlUtils.toRelativeUrl(websiteValidate.getShopLogo()));

        ConfigUtils.set("website", "pcLogo", UrlUtils.toRelativeUrl(websiteValidate.getPcLogo()));
        ConfigUtils.set("website", "pcIco", UrlUtils.toRelativeUrl(websiteValidate.getPcIco()));
        ConfigUtils.set("website", "pcTitle", websiteValidate.getPcTitle());
        ConfigUtils.set("website", "pcDesc", websiteValidate.getPcDesc());
        ConfigUtils.set("website", "pcKeywords", websiteValidate.getPcKeywords());
    }

}
