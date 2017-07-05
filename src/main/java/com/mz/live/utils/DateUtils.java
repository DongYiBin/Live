/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
public class DateUtils {

	private static final String FORMAT_yyyyMMddHHmmss = "yyyyMMddHHmmss";

	public static String getyyyyMMddHHmmss(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_yyyyMMddHHmmss);
		return format.format(date);
	}

	public static String getyyyyMMddHHmmss() {
		return getyyyyMMddHHmmss(new Date());
	}

	public static Date dateFormatter(Long time) {
		if(null == time){
			return null;
		}
		if(time.toString().length() == 13){
			return new Date(time);
		}
		if(time.toString().length() == 10){
			return new Date(time * 1000);
		}
		return null;
	}
}
