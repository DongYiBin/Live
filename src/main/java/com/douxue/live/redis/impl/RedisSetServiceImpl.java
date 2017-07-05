
/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */package com.douxue.live.redis.impl;

import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

import com.douxue.live.redis.RedisSetService;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
public class RedisSetServiceImpl extends RedisKeyServiceImpl implements RedisSetService {

	public Long add(String key, Object... values) {
		String[] jsons = this.objsToStrings(values);
		return redisTemplate.opsForSet().add(key, jsons);
	}

	public Long size(String key) {
		return redisTemplate.opsForSet().size(key);
	}

	public Boolean isMember(String key, Object value) {
		return redisTemplate.opsForSet().isMember(key, converter.toString(value));
	}

	public Long remove(String key, Object... values) {
		String[] jsons = this.objsToStrings(values);
		return redisTemplate.opsForSet().remove(key, (Object[]) jsons);
	}

	public Set<String> members(String key) {
		return redisTemplate.opsForSet().members(key);
	}

	public <T> Set<T> members(String key, Class<T> clazz) {
		return this.members(key, (Type) clazz);
	}

	public <T> Set<T> members(String key, Type type) {
		Set<String> sMemberSet = redisTemplate.opsForSet().members(key);
		Set<T> objSet = new LinkedHashSet<T>();

		for (String member : sMemberSet) {
			T t = converter.fromString(member, type);
			objSet.add(t);
		}

		return objSet;
	}

	private String[] objsToStrings(Object... object) {
		String[] jsons = new String[object.length];
		for (int i = 0; i < jsons.length; i++) {
			jsons[i] = converter.toString(object[i]);
		}
		return jsons;
	}

}
