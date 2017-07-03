package com.mz.live.redis.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mz.live.redis.RedisHashService;

public class RedisHashServiceImpl extends RedisKeyServiceImpl implements RedisHashService {

	public String getHashValue(String key, String field) {
		return redisTemplate.<String, String> opsForHash().get(key, field);
	}

	public <T> T getHashValue(String key, String field, Class<T> clazz) {
		return this.getHashValue(key, field, (Type) clazz);
	}

	public <T> T getHashValue(String key, String field, Type type) {
		return converter.fromString(this.getHashValue(key, field), type);
	}

	public boolean setHashValue(String key, String field, Object value) {
		String stringValue = converter.toString(value);
		redisTemplate.<String, String> opsForHash().put(key, field, stringValue);

		return true;
	}

	public List<String> getHashMutiplValues(String Key, Collection<String> fields) {
		return redisTemplate.<String, String> opsForHash().multiGet(Key, fields);
	}

	public <T> List<T> getHashMutiplValues(String Key, Collection<String> fields, Class<T> clazz) {
		return this.getHashMutiplValues(Key, fields, (Type) clazz);
	}

	public <T> List<T> getHashMutiplValues(String Key, Collection<String> fields, Type type) {
		List<T> objList = new ArrayList<T>();
		List<String> stringList = redisTemplate.<String, String> opsForHash().multiGet(Key, fields);
		for (String ele : stringList) {
			T t = converter.fromString(ele, type);
			objList.add(t);
		}
		return objList;
	}

	public boolean deleteHashValue(String Key, String field) {
		redisTemplate.<String, String> opsForHash().delete(Key, field);
		return true;
	}

	public long size(String Key) {
		return redisTemplate.<String, String> opsForHash().size(Key);
	}

	public Map<String, String> getHashAllValue(String Key) {
		return redisTemplate.<String, String> opsForHash().entries(Key);
	}

	public <T> Map<String, T> getHashAllValue(String Key, Class<T> clazz) {
		return this.getHashAllValue(Key, (Type) clazz);
	}

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
