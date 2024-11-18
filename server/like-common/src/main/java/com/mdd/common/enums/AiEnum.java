package com.mdd.common.enums;

/**
 * @author xmg
 * @date 2023/12/6 23:23
 */
public enum AiEnum {
    /**
     * 点数使用场景
     */

    AiArtCode("艺术二维码", "aiArtCode"),

    MjImagine("MJ文生图", "mjImagine"),

    MjFaceswap("MJ换脸", "faceswap"),

    GptDalle3("Dalle3绘画", "dalle3"),


    /**
     * MJ绘画返回值
     */
    MjImagineRes1("提交成功", "1"),
    MjImagineRes22("排队中", "22"),
    MjImagineRes23("队列已满队列已满", "23"),
    MjImagineRes24("prompt包含敏感词", "24"),
    MjImagineResOther("错误", "other");


    private String name;
    private String value;

    AiEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
