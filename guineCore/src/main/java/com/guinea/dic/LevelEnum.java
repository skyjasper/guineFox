package com.guinea.dic;

/**
 * @title 级别
 * @author: shiky
 * @describe:
 * @date: 2016/6/27
 */
public enum LevelEnum {
    GENNER(Short.valueOf("0"), "游离"), FINAL(Short.valueOf("1"), "普通"),
    IMPORT(Short.valueOf("2"),"重要"),VERYIMPORT(Short.valueOf("3"),"重大");

    private Short code;

    private String name;

    private LevelEnum(Short code, String name) {
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
        for (LevelEnum c : LevelEnum.values()) {
            if (c.getCode().equals(code))
                result = c.name;
        }
        return result;
    }
}
