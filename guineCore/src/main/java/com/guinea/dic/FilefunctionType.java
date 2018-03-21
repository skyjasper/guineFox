package com.guinea.dic;

/**
 * @author: shiky
 * @describle:
 * @dateTime: 2016/3/8
 */
public enum FilefunctionType {

    SYS(Short.valueOf("1"), "系统文档"), TX(Short.valueOf("2"), "头像"),ZL(Short.valueOf("3"),"资料"),TP(Short.valueOf("4"),"图片");
    private Short code;

    private String name;

    private FilefunctionType(Short code, String name) {
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
        for (FilefunctionType c : FilefunctionType.values()) {
            if (c.getCode() == code)
                result = c.name;
        }
        return result;
    }
}
