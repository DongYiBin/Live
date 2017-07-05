/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.redis.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.douxue.live.redis.RedisListService;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
public class RedisListServiceImpl extends RedisKeyServiceImpl implements RedisListService {

	public Long size(String key) {
		return redisTemplate.opsForList().size(key);
	}

	public List<String> getListValues(String key, long start, long end) {
		return redisTemplate.opsForList().range(key, start, end);
	}

	public <T> List<T> getListValues(String key, long start, long end, Class<T> clazz) {
		return this.getListValues(key, start, end, (Type) clazz);
	}

	public <T> List<T> getListValues(String key, long start, long end, Type type) {
		List<T> objList = new ArrayList<T>();
		List<String> values = getListValues(key, start, end);

		for (String value : values) {
			T t = converter.fromString(value, type);
			objList.add(t);
		}
		return objList;
	}

	public long pushListValueFromRight(String key, Object value) {
		String sValue = converter.toString(value);
		return redisTemplate.opsForList().rightPush(key, sValue);
	}

	public long pushListValueFromLeft(String key, Object value) {
		String sValue = converter.toString(value);
		return redisTemplate.opsForList().leftPush(key, sValue);
	}

	public String popListValueFromLeft(String key) {
		return redisTemplate.opsForList().leftPop(key);
	}

	public <T> T popListValueFromLeft(String key, Class<T> clazz) {
		return this.popListValueFromLeft(key, (Type) clazz);
	}

	public <T> T popListValueFromLeft(String key, Type type) {
		return converter.fromString(popListValueFromLeft(key), type);
	}

	public String popListValueFromRight(String key) {
		return redisTemplate.opsForList().rightPop(key);
	}

	public <T> T popListValueFromRight(String key, Class<T> clazz) {
		return this.popListValueFromRight(key, (Type) clazz);
	}

	public <T> T popListValueFromRight(String key, Type type) {
		return converter.fromString(popListValueFromRight(key), type);
	}

}
