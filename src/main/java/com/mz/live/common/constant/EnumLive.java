package com.mz.live.common.constant;

public enum EnumLive {

	STATE_CREATE("初始化", -1),
	STATE_STOP("已结束", 0), 
	STATE_PUSH("直播中", 1),
	STATE_FORBID("禁播", -2),
	STATE_PLAY("回放", 100),
	STATE_SCREENSHO("截图", 200);

	private String name;
	private int value;

	private EnumLive(String name, int value) {
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
