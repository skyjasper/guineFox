package com.guinea.dic;

/**
 * Created by shiky on 2015/12/28.
 */
public enum ResourceTypeEnum {

    KY("button", "按钮"), BKY("menu", "菜单");

    private String code,name;

    private ResourceTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public static String getName(String code) {
        String result = "";
        for (ResourceTypeEnum c : ResourceTypeEnum.values()) {
            if (c.getCode().equals(code))
                result = c.name;
        }
        return result;
    }
}
