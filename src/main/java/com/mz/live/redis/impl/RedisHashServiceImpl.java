package com.mz.live.redis.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mz.live.redis.RedisHashService;

public class RedisHashServiceImpl extends RedisKeyServiceImpl implements RedisHashService {

	/**
	 * 获取指定hash指定域的值.
	 */
	public String getHashValue(String key, String field) {
		return redisTemplate.<String, String> opsForHash().get(key, field);
	}

	/**
	 * 获取指定hash指定域的值，并转换为指定类型.
	 */
	public <T> T getHashValue(String key, String field, Class<T> clazz) {
		return this.getHashValue(key, field, (Type) clazz);
	}

	/**
	 * 讀取指定hash指定域的值，并转换为指定类型，适合泛型类型.
	 */
	public <T> T getHashValue(String key, String field, Type type) {
		return converter.fromString(this.getHashValue(key, field), type);
	}

	/**
	 * 設置指定hash指定域的值. 
	 */
	public boolean setHashValue(String key, String field, Object value) {
		String stringValue = converter.toString(value);
		redisTemplate.<String, String> opsForHash().put(key, field, stringValue);

		return true;
	}

	/**
	 * 批量获取指定hash某些域的值. 
	 */
	public List<String> getHashMutiplValues(String Key, Collection<String> fields) {
		return redisTemplate.<String, String> opsForHash().multiGet(Key, fields);
	}

	/**
	 * 批量获取指定hash某些域的值. 
	 */
	public <T> List<T> getHashMutiplValues(String Key, Collection<String> fields, Class<T> clazz) {
		return this.getHashMutiplValues(Key, fields, (Type) clazz);
	}

	/**
	 * 批量获取指定hash某些域的值，并转换为指定类型，适合泛型类型. 
	 */
	public <T> List<T> getHashMutiplValues(String Key, Collection<String> fields, Type type) {
		List<T> objList = new ArrayList<T>();
		List<String> stringList = redisTemplate.<String, String> opsForHash().multiGet(Key, fields);
		for (String ele : stringList) {
			T t = converter.fromString(ele, type);
			objList.add(t);
		}
		return objList;
	}

	/**
	 * 删除指定hash的指定域. 
	 */
	public boolean deleteHashValue(String Key, String field) {
		redisTemplate.<String, String> opsForHash().delete(Key, field);
		return true;
	}

	/**
	 * 获取hash的元素个数. 
	 */
	public long size(String Key) {
		return redisTemplate.<String, String> opsForHash().size(Key);
	}

	/**
	 * 获取hash中所有的元素. 
	 */
	public Map<String, String> getHashAllValue(String Key) {
		return redisTemplate.<String, String> opsForHash().entries(Key);
	}

	/**
	 * 获取hash中所有的元素，并将value转换为指定类型. 
	 */
	public <T> Map<String, T> getHashAllValue(String Key, Class<T> clazz) {
		return this.getHashAllValue(Key, (Type) clazz);
	}

	/**
	 * 获取hash中所有的元素，并将value转换为指定类型.
	 */
	public <T> Map<String, T> getHashAllValue(String Key, Type type) {
		Map<String, T> objMap = new LinkedHashMap<String, T>();
		Map<String, String> stringMap = redisTemplate.<String, String> opsForHash().entries(Key);
		for (Map.Entry<String, String> entry : stringMap.entrySet()) {
			String jsonString = entry.getValue();
			T t = converter.fromString(jsonString, type);
			objMap.put(entry.getKey(), t);

		}
		return objMap;
	}

	public Long incrBy(String key, String field, long delta) {
		return redisTemplate.<String, String> opsForHash().increment(key, field, delta);
	}

}
