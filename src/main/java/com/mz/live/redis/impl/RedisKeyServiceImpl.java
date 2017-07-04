/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.redis.impl;

import org.springframework.data.redis.core.RedisTemplate;

import com.mz.live.redis.RedisKeyService;
import com.mz.live.redis.convertor.GsonStringConverter;
import com.mz.live.redis.convertor.StringConverter;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
public class RedisKeyServiceImpl implements RedisKeyService {

	protected RedisTemplate<String, String> redisTemplate;

	protected StringConverter converter = new GsonStringConverter();

	public RedisTemplate<String, String> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public StringConverter getConverter() {
		return converter;
	}

	public void setConverter(StringConverter converter) {
		this.converter = converter;
	}

	public boolean expired(String Key, long timeInSeconds) {
		return redisTemplate.expire(Key, timeInSeconds, TimeUnit.SECONDS);
	}

	public boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}

	public boolean delete(String key) {
		redisTemplate.delete(key);
		return true;
	}

	public boolean delete(Collection<String> keys) {
		redisTemplate.delete(keys);
		return true;
	}

}
