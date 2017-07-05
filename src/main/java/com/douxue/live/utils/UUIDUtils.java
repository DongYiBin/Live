/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.utils;

import java.util.UUID;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
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
