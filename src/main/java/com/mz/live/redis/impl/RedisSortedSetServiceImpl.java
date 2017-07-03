package com.mz.live.redis.impl;

import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mz.live.redis.RedisSortedSetService;

public class RedisSortedSetServiceImpl extends RedisKeyServiceImpl implements RedisSortedSetService {

	public Long size(String key) {
		return redisTemplate.opsForZSet().zCard(key);
	}

	public Boolean addValuetoSortedSet(String key, Object value, double score) {
		String json = converter.toString(value);
		return redisTemplate.opsForZSet().add(key, json, score);
	}

	public Long removeValuesfromSortedSet(String key, Object... values) {
		String[] jsons = this.objsToStrings(values);
		return redisTemplate.opsForZSet().remove(key, (Object[]) jsons);
	}

	public Set<String> getValuesfromSortedSet(String key, long start, long end) {
		return redisTemplate.opsForZSet().range(key, start, end);
	}

	public <T> Set<T> getValuesfromSortedSet(String key, long start, long end, Class<T> clazz) {
		Set<String> sMemberSet = this.getValuesfromSortedSet(key, start, end);
		return this.stringSetToObjectSet(sMemberSet, (Type) clazz);

	}

	public <T> Set<T> getValuesfromSortedSet(String key, long start, long end, Type type) {
		Set<String> sMemberSet = this.getValuesfromSortedSet(key, start, end);
		return this.stringSetToObjectSet(sMemberSet, type);

	}

	private String[] objsToStrings(Object... object) {
		String[] jsons = new String[object.length];
		for (int i = 0; i < jsons.length; i++) {
			jsons[i] = converter.toString(object[i]);
		}
		return jsons;
	}

	private <T> Set<T> stringSetToObjectSet(Set<String> stringSet, Type type) {
		Set<T> objSet = new LinkedHashSet<T>();
		for (String member : stringSet) {
			T t = converter.fromString(member, type);
			objSet.add(t);
		}
		return objSet;
	}

}
