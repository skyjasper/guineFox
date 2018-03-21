package com.guinea.dic;

/**
 * @title 执行类型
 * @author: shiky
 * @describe:
 * @date: 2016/6/27
 */
public enum ExecuteTypeEnum {

    EVERYAY(Short.valueOf("0"), "每天"), EREYMONTH(Short.valueOf("1"), "每月"),
    TIMING(Short.valueOf("2"),"定时");

    private Short code;

    private String name;

    private ExecuteTypeEnum(Short code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Short getCode() {
        return code;
    }

    public static String getName(Short code) {
        String result = "";
        for (ExecuteTypeEnum c : ExecuteTypeEnum.values()) {
            if (c.getCode().equals(code))
                result = c.name;
        }
        return result;
    }
}
