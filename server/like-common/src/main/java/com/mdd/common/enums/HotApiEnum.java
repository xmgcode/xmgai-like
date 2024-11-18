package com.mdd.common.enums;

/**
 * @author zhangyujun
 * @date 2023/12/6 23:23
 */
public enum HotApiEnum {

    WANGYI("wangyi", "https://m.163.com/fe/api/hot/news/flow"),
    BILIBILI("bilibili", "https://api.bilibili.com/x/web-interface/ranking/v2"),

    DOUYIN("douyin","https://aweme-hl.snssdk.com/aweme/v1/hot/search/list/?detail_list=1"),

    JUEJIN("juejin","https://api.juejin.cn/content_api/v1/content/article_rank?category_id=1&type=hot"),

    LOL("lol","https://apps.game.qq.com/cmc/zmMcnTargetContentList?r0=jsonp&page=1&num=50&target=24&source=web_pc&r1=jQuery191002324053053181463_1687855508930&_=1687855508933"),

    PAPER("paper","https://cache.thepaper.cn/contentapi/wwwIndex/rightSidebar"),

    SOUGOU("sougou","https://hotlist.imtt.qq.com/Fetch"),

    SSPAI("sspai","https://sspai.com/api/v1/article/tag/page/get?limit=50&tag=%E7%83%AD%E9%97%A8%E6%96%87%E7%AB%A0"),

    TENCENT("tencent","https://r.inews.qq.com/gw/event/hot_ranking_list?page_size=50"),

    TIEBA("tieba","https://tieba.baidu.com/hottopic/browse/topicList"),

    TOUTIAO("toutiao","https://www.toutiao.com/hot-event/hot-board/?origin=toutiao_pc"),

    WEIBO("weibo","https://weibo.com/ajax/side/hotSearch"),

    WERAED("weread","https://weread.qq.com/web/bookListInCategory/rising?rank=1"),

    ZHIHU("zhihu","https://api.zhihu.com/topstory/hot-list");


    private String key;
    private String value;

    HotApiEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
