package com.mz.live.utils;

import java.util.UUID;

public class UUIDUtils {

	public static String createUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取UUID qudia
	 * @return
	 * @author zhaodun
	 */
	public static String getUUID() {
		return createUUID().replace("-", "");
	}
}
