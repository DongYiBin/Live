package com.mz.live.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

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
