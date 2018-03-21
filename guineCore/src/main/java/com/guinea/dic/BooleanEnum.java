package com.guinea.dic;

/**
 * @author: shiky
 * @describle:
 * @dateTime: 2016/2/26
 */
public enum BooleanEnum {
    TCAN(Short.valueOf("1"), "是"), NOCAN(Short.valueOf("0"), "否");

    private Short code;

    private String name;

    private BooleanEnum(Short code, String name) {
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
        for (BooleanEnum c : BooleanEnum.values()) {
            if (c.getCode().equals(code))
                result = c.name;
        }
        return result;
    }
}
