package com.guinea.dic;


/***
 * 
 * @author shiky
 *性别
 */
public enum SexEnum {
	 WZ(Short.valueOf("-1"), "未知"), NAN(Short.valueOf("0"), "男"),NV(Short.valueOf("1"),"女"),
	    WU(Short.valueOf("2"),"无性别"),SHX(Short.valueOf("3"),"双性人"),NVONAN(Short.valueOf("4"),"女变男"),
	    NANONV(Short.valueOf("5"),"男变女"),NHZ(Short.valueOf("6"),"女汉子"),WN(Short.valueOf("7"),"伪娘");

	    private Short code;

	    private String name;

	    private SexEnum(Short code, String name) {
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
	        for (SexEnum c : SexEnum.values()) {
	            if (c.getCode() == code)
	                result = c.name;
	        }
	        return result;
	    }
}
