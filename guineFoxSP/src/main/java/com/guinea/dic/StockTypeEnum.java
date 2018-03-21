package com.guinea.dic;

/**
 * Created by shiky on 2015/12/9.
 */
public enum StockTypeEnum {
    HA(Short.valueOf("1"), "上A"), YA(Short.valueOf("2"), "深A"), H(Short.valueOf("3"), "港");

    private Short code;

    private String name;

    private StockTypeEnum(Short code, String name) {
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
        for (StockTypeEnum c : StockTypeEnum.values()) {
            if (c.getCode() == code)
                result = c.name;
        }
        return result;
    }
}
