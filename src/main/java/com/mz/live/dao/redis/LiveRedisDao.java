package com.mz.live.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mz.live.redis.RedisHashService;

@Repository
public class LiveRedisDao {

	@Autowired
	private RedisHashService redisHashService;

	// 直播Redis KEY 前缀
	private static final String LIVE_KEY_PREFIX = "LIVE_";
	// 点赞域 KEY
	private static final String LIVE_PRAISE_COUNT_FIELD = "PRAISE_COUNT";
	// 播放次数域KEY
	private static final String LIVE_VIEWER_COUNT_FIELD = "VIEWER_COUNT";

	private String getLiveKey(String streamId) {
		return new StringBuilder().append(LIVE_KEY_PREFIX).append(streamId).toString();
	}

	/**
	 * 添加点赞数
	 * 
	 * @param streamId
	 * @param praiseNum
	 * @return
	 * @author zhaodun
	 */
	public long addPraiseLive(String streamId, int num) {
		String key = getLiveKey(streamId);
		return redisHashService.incrBy(key, LIVE_PRAISE_COUNT_FIELD, num);
	}

	/**
	 * 获取点赞数
	 * 
	 * @param streamId
	 * @return
	 * @author zhaodun
	 */
	public long getPraiseCount(String streamId) {
		String key = getLiveKey(streamId);
		Long praiseCnt = redisHashService.getHashValue(key, LIVE_PRAISE_COUNT_FIELD, Long.class);
		return praiseCnt != null ? praiseCnt : 0;
	}

	/**
	 * 添加观众数
	 * 
	 * @param streamId
	 * @param playNum
	 * @return
	 * @author zhaodun
	 */
	public long addLiveViewerNum(String streamId, int num) {
		String key = getLiveKey(streamId);
		return redisHashService.incrBy(key, LIVE_VIEWER_COUNT_FIELD, num);
	}

	/**
	 * 获取观众数
	 * 
	 * @param streamId
	 * @return
	 * @author zhaodun
	 */
	public long getLiveViewerCount(String streamId) {
		String key = getLiveKey(streamId);
		Long viewerCnt = redisHashService.getHashValue(key, LIVE_VIEWER_COUNT_FIELD, Long.class);
		return viewerCnt != null ? viewerCnt : 0;
	}

}
