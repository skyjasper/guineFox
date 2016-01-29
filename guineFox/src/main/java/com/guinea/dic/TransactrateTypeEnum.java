package com.guinea.dic;

/**
 * Created by shiky on 2015/9/22.
 */
public enum TransactrateTypeEnum {

    HA(Short.valueOf("1"), "买入"), YA(Short.valueOf("2"), "卖出");

    private Short code;

    private String name;

    private TransactrateTypeEnum(Short code, String name) {
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
        for (TransactrateTypeEnum c : TransactrateTypeEnum.values()) {
            if (c.getCode() == code)
                result = c.name;
        }
        return result;
    }
}
