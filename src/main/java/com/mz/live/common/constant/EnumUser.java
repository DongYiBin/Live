package com.mz.live.common.constant;

public enum EnumUser {

	STATE_NORMAL("正常", 0),
	STATE_STOP("停用", -1),
	
	ACCOUNT_TYPE_COMMON("普通", 0),
	ACCOUNT_TYPE_WX("微信", 1);

	private String name;
	private int value;

	private EnumUser(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public String getName() {
		return this.name;
	}
}
