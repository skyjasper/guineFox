package com.guinea.dic;

/**
 * Created by shiky on 2015/12/9.
 */
public enum StockPaceEnum {
    ZB(Short.valueOf("1"), "主板"), ZXB(Short.valueOf("2"), "中小板"),CYB(Short.valueOf("3"),"创业板"),QT(Short.valueOf("4"),"-");
    private Short code;

    private String name;

    private StockPaceEnum(Short code, String name) {
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
        for (StockPaceEnum c : StockPaceEnum.values()) {
            if (c.getCode() == code)
                result = c.name;
        }
        return result;
    }
}
