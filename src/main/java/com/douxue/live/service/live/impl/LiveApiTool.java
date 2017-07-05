/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.service.live.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.douxue.live.utils.MD5Utils;

/**
 * 直播使用的工具
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
@Service
public class LiveApiTool {

	@Value("${live_appid}")
	private String LIVE_APP_ID;
	@Value("${live_bizid}")
	private String LIVE_BIZID;
	@Value("${live_push_safe_key}")
	private String LIVE_PUSH_SAFE_KEY;
	@Value("${live_api_sign_key}")
	private String LIVE_API_SIGN_KEY;

	@Value("${live_push_rtmp_url}")
	private String LIVE_PUSH_RTMP_URL;
	@Value("${live_play_rtmp_url}")
	private String LIVE_PLAY_RTMP_URL;
	@Value("${live_play_flv_url}")
	private String LIVE_PLAY_FLV_URL;
	@Value("${live_play_hls_url}")
	private String LIVE_PLAY_HLS_URL;
	@Value("${live_record}")
	private String LIVE_RECORD;

	/**
	 * 生成防盗链
	 * 
	 * @param key
	 * @param streamId
	 * @param txTime
	 * @return
	 */
	public String getSafeUrl(String key, String streamId, long txTime) {
		String input = new StringBuilder().append(key).append(streamId).append(Long.toHexString(txTime).toUpperCase()).toString();
		String txSecret = null;
		txSecret = MD5Utils.encrypt(input);
		return txSecret == null ? "" : new StringBuilder().append("&txSecret=").append(txSecret).append("&").append("txTime=").append(Long.toHexString(txTime).toUpperCase()).toString();
	}

	/**
	 * 获取拼接推流地址
	 * 
	 * @param userId
	 * @param streamId
	 * @param txTime
	 * @return
	 */
	public String getPushRtmpUrl(String streamId, long txTime) {
		StringBuffer pushUrl = new StringBuffer();
		pushUrl.append(String.format(LIVE_PUSH_RTMP_URL, LIVE_BIZID, streamId, LIVE_BIZID));
		// 回放生成格式
		if (null != LIVE_RECORD && !"".equals(LIVE_RECORD)) {
			pushUrl.append("&record=").append(LIVE_RECORD);
		}
		pushUrl.append(getSafeUrl(LIVE_PUSH_SAFE_KEY, streamId, txTime));
		return pushUrl.toString();
	}

	/**
	 * 获取播放拉流地址
	 * 
	 * @param streamId
	 * @return
	 */
	public String getPlayRtmpUrl(String streamId) {
		return String.format(LIVE_PLAY_RTMP_URL, LIVE_BIZID, streamId);
	}

	/**
	 * 获取FLV播放地址
	 * 
	 * @param streamId
	 * @return
	 */
	public String getPlayFlvURL(String streamId) {
		return String.format(LIVE_PLAY_FLV_URL, LIVE_BIZID, streamId);
	}

	/**
	 * 获取HLS播放地址
	 * 
	 * @param streamId
	 * @return
	 */
	public String getPlayHlsUrl(String streamId) {
		return String.format(LIVE_PLAY_HLS_URL, LIVE_BIZID, streamId);
	}
	
	/**
	 * 回调签名验证
	 * @param sign
	 * @param t
	 * @return
	 * @author zhaodun
	 */
	public boolean callbackSignAuth(String sign, long t){
		String verify = MD5Utils.encrypt(LIVE_API_SIGN_KEY + t);
		return sign.equals(verify);
	}
	
	/**
	 * 生成签名
	 * @param t
	 * @return
	 * @author zhaodun
	 */
	public String getSign(long t){
		return MD5Utils.encrypt(LIVE_API_SIGN_KEY + t);
	}
	
}
