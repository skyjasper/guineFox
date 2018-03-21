package com.guinea.dic;

/**
 * @title 完成状态
 * @author: shiky
 * @describe:
 * @date: 2016/6/27
 */
public enum FinishEnum {
    NO(Short.valueOf("0"), "未完成"), YES(Short.valueOf("1"), "完成"),
    NOFIND(Short.valueOf("2"), "挂起");

    private Short code;

    private String name;

    private FinishEnum(Short code, String name) {
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
        for (FinishEnum c : FinishEnum.values()) {
            if (c.getCode().equals(code))
                result = c.name;
        }
        return result;
    }
}
