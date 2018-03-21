package com.guinea.dic;

/**
 * @title 状态
 * @author: shiky
 * @describe:
 * @date: 2016/6/27
 */
public enum PastEnum {
    NO(Short.valueOf("0"), "未过时"), YES(Short.valueOf("1"), "已过时"),
    NOFIND(Short.valueOf("2"), "挂起");

    private Short code;

    private String name;

    private PastEnum(Short code, String name) {
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
        for (PastEnum c : PastEnum.values()) {
            if (c.getCode().equals(code))
                result = c.name;
        }
        return result;
    }
}
