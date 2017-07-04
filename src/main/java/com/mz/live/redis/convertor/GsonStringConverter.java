/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.redis.convertor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
public class GsonStringConverter implements StringConverter {

	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	public <T> String toString(T t) {
		if (t instanceof String) {
			return (String) t;
		}
		return gson.toJson(t);
	}

	/**
	 * 将字符串转换为指定类型的对象.适用于非泛型类型.
	 */
	public <T> T fromString(String value, Class<T> clazz) {
		return gson.fromJson(value, clazz);
	}

	public <T> T fromString(String value, Type type) {
		return gson.fromJson(value, type);
	}

}
