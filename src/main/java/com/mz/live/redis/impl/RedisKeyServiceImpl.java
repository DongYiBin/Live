package com.mz.live.redis.impl;

import org.springframework.data.redis.core.RedisTemplate;

import com.mz.live.redis.RedisKeyService;
import com.mz.live.redis.convertor.GsonStringConverter;
import com.mz.live.redis.convertor.StringConverter;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

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

	/**
	 * 设置某个key的时效时间.
	 */
	public boolean expired(String Key, long timeInSeconds) {
		return redisTemplate.expire(Key, timeInSeconds, TimeUnit.SECONDS);
	}

	/**
	 * 判断指定key是否存在. 
	 */
	public boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 批量删除key
	 */
	public boolean delete(String key) {
		redisTemplate.delete(key);
		return true;
	}

	/**
	 * 批量删除key
	 */
	public boolean delete(Collection<String> keys) {
		redisTemplate.delete(keys);
		return true;
	}

}
