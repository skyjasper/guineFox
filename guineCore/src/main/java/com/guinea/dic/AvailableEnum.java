package com.guinea.dic;

public enum AvailableEnum {
	
	KY(Short.valueOf("0"), "不可用"), BKY(Short.valueOf("1"), "可用");
	
	private Short code;

	private String name;

	private AvailableEnum(Short code, String name) {
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
		for (AvailableEnum c : AvailableEnum.values()) {
			if (c.getCode().equals(code))
				result = c.name;
		}
		return result;
	}
}
