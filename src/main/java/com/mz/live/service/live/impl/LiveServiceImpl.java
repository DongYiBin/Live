/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.service.live.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mz.live.common.RestResult;
import com.mz.live.common.constant.EnumLive;
import com.mz.live.common.constant.ErrorCode;
import com.mz.live.common.page.PageData;
import com.mz.live.common.page.PageParam;
import com.mz.live.controller.app.callback.vo.LiveCalbackVo;
import com.mz.live.dao.entity.Live;
import com.mz.live.dao.entity.LiveVideo;
import com.mz.live.dao.entity.User;
import com.mz.live.dao.mapper.LiveMapper;
import com.mz.live.dao.mapper.LiveVideoMapper;
import com.mz.live.dao.mapper.UserMapper;
import com.mz.live.dao.redis.LiveRedisDao;
import com.mz.live.service.live.LiveService;
import com.mz.live.utils.DateUtils;
import com.mz.live.utils.HttpClientUtils;
import com.mz.live.utils.UUIDUtils;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
@Service
public class LiveServiceImpl implements LiveService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private Gson gson = new GsonBuilder().create();

	private static final long TIMEOUT = 1800;
	@Value("${live_appid}")
	private String LIVE_APP_ID;
	@Value("${live_bizid}")
	private String LIVE_BIZID;
	@Value("${live_common_access_url}")
	private String LIVE_COMMON_ACCESS_URL;

	@Autowired
	private LiveApiTool liveApiTool;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private LiveMapper liveMapper;
	@Autowired
	private LiveRedisDao liveRedisDao;
	@Autowired
	private LiveVideoMapper liveVideoMapper;

	private Live setLiveRedisData(Live live) {
		if (null == live) {
			return null;
		}
		// 观看次数
		live.setViewerCount(liveRedisDao.getLiveViewerCount(live.getStreamId()));
		// 点赞
		live.setPraiseCount(liveRedisDao.getPraiseCount(live.getStreamId()));
		return live;
	}

	@Override
	@Transactional
	public RestResult createLive(Live create) {
		User user = userMapper.selectByPrimaryKey(create.getUserId());
		if (null == user) {
			return new RestResult(ErrorCode.ERROR_TARGET_USER_NO_EXIST);
		}
		// 创建直播
		String streamId = LIVE_BIZID + "_" + UUIDUtils.getUUID(); // 生成直播ID
		create.setStreamId(streamId);
		create.setCreateTime(new Date());
		create.setState(EnumLive.STATE_CREATE.getValue());// 设置直播为初始化状态
		// 拼接推流地址
		create.setPushUrl(liveApiTool.getPushRtmpUrl(streamId, System.currentTimeMillis() / 1000 + TIMEOUT));
		// 拼接拉流地址
		create.setPlayRtmpUrl(liveApiTool.getPlayRtmpUrl(streamId));
		create.setPlayFlvUrl(liveApiTool.getPlayFlvURL(streamId));
		create.setPlayHlsUrl(liveApiTool.getPlayHlsUrl(streamId));
		if (liveMapper.insertSelective(create) > 0) {
			Live live = liveMapper.selectByPrimaryKey(streamId);
			return new RestResult(live);
		}
		return RestResult.FAIL;
	}

	@Override
	public Live getLiveByStreamId(String streamId) {
		Live live = liveMapper.selectByPrimaryKey(streamId);
		live = setLiveRedisData(live);
		return live;
	}

	@Override
	public PageData<Live> getAppLivePage(Integer pageNum, Integer pageSize, Live live) {
		PageParam<Live> param = new PageParam<Live>(live, pageNum, pageSize);
		List<Live> data = liveMapper.getAppLivePage(param);
		if (null != data && data.size() > 0) {
			for (Live l : data) {
				l = setLiveRedisData(l);
			}
		}
		PageData<Live> page = new PageData<Live>(pageNum, pageSize, param.getDataTotal(), data);
		return page;
	}

	@Override
	public Live addLivePraiseNum(String streamId, int num) {
		Live live = liveMapper.selectByPrimaryKey(streamId);
		if (null == live) {
			return null;
		}
		if (liveRedisDao.addPraiseLive(streamId, num) > 0) {
			live = liveMapper.selectByPrimaryKey(streamId);
			live = setLiveRedisData(live);
			live.getUser().setPassword(null);
			return live;
		}
		return null;
	}

	@Override
	public Live addLiveViewerNum(String streamId, int num) {
		Live live = liveMapper.selectByPrimaryKey(streamId);
		if (null == live) {
			return null;
		}
		if (liveRedisDao.addLiveViewerNum(streamId, num) > 0) {
			live = liveMapper.selectByPrimaryKey(streamId);
			live = setLiveRedisData(live);
			live.getUser().setPassword(null);
			return live;
		}
		return null;
	}

	@Override
	public boolean liveCallBackDispose(LiveCalbackVo vo) {
		if (null != vo && null != vo.getStream_id()) {
			Live live = liveMapper.selectByPrimaryKey(vo.getStream_id());
			if (null != live && vo != null) {
				// event_type = 0 断流
				if (vo.getEvent_type() == EnumLive.STATE_STOP.getValue()) {
					// 断流后更改直播为回放状态
					Live change = new Live();
					change.setStreamId(live.getStreamId());
					change.setState(EnumLive.STATE_PLAY.getValue());
					if (liveMapper.updateByPrimaryKeySelective(change) > 0) {
						return true;
					}
				}
				// event_type = 100 新录制文件
				if (vo.getEvent_type() == EnumLive.STATE_PLAY.getValue() 
						&& null != vo.getVideo_url() && !"".equals(vo.getVideo_url())) {
					// 插入点播切片
					LiveVideo video = new LiveVideo();
					// 设置streamId关联关系
					video.setStreamId(vo.getStream_id());
					video.setVideoId(vo.getVideo_id());
					video.setVideoUrl(vo.getVideo_url());
					video.setFileSize(vo.getFile_size());
					video.setFileId(vo.getFile_id());
					video.setFileFormat(vo.getFile_format());
					video.setStartTime(DateUtils.dateFormatter(vo.getStart_time()));
					video.setEndTime(DateUtils.dateFormatter(vo.getEnd_time()));
					video.setCreateTime(new Date());
					if (liveVideoMapper.insertSelective(video) > 0) {
						return true;
					}
				}
				// event_type = 1 推流 表示直播开始推流 : 不做数据处理
				if (vo.getEvent_type() == EnumLive.STATE_PUSH.getValue()) {
					return true;
				}
				// event_type = 200新截图文件: 不做数据处理
				if (vo.getEvent_type() == EnumLive.STATE_PLAY.getValue()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Live pushStart(String streamId) {
		Live live = liveMapper.selectByPrimaryKey(streamId);
		if (null != live) {
			Live changeLive = new Live();
			changeLive.setStreamId(live.getStreamId());
			changeLive.setState(EnumLive.STATE_PUSH.getValue());
			changeLive.setStartTime(new Date());
			if (liveMapper.updateByPrimaryKeySelective(changeLive) > 0) {
				return liveMapper.selectByPrimaryKey(streamId);
			}
		}
		return null;
	}

	@Override
	public Live pushStop(String streamId) {
		Live live = liveMapper.selectByPrimaryKey(streamId);
		if (null != live) {
			Live changeLive = new Live();
			changeLive.setStreamId(live.getStreamId());
			changeLive.setState(EnumLive.STATE_STOP.getValue());
			if (liveMapper.updateByPrimaryKeySelective(changeLive) > 0) {
				return liveMapper.selectByPrimaryKey(streamId);
			}
		}
		return null;
	}

	@Override
	public PageData<Live> findLivePageList(Integer pageNum, Integer pageSize, Live live) {
		PageParam<Live> param = new PageParam<Live>(live, pageNum, pageSize);
		List<Live> data = liveMapper.findLivePageList(param);
		if (null != data && data.size() > 0) {
			for (Live l : data) {
				l = setLiveRedisData(l);
			}
		}
		PageData<Live> page = new PageData<Live>(pageNum, pageSize, param.getDataTotal(), data);
		return page;
	}

	@Override
	public RestResult forbidLive(String streamId){
		// 根据直播streamId 获取直播数据
		Live live = liveMapper.selectByPrimaryKey(streamId);
		if (null == live) {
			return new RestResult(ErrorCode.ERROR_NULL_DATA);
		}
		LinkedHashMap<String, Object> paramMap = new LinkedHashMap<String, Object>();
		// 设置签名过期时间
		long t = System.currentTimeMillis() / 1000 + TIMEOUT;
		// 拼接API参数
		paramMap.put("cmd", LIVE_APP_ID);
		paramMap.put("interface", "Live_Channel_SetStatus");
		paramMap.put("t", t);
		paramMap.put("sign", liveApiTool.getSign(t)); // 签名
		paramMap.put("Param.s.channel_id", streamId);
		paramMap.put("Param.n.status", 0); // 设置流状态 int 0:关闭； 1:开启
		String res = "";
		try {
			// 调用API强制断流
			res = HttpClientUtils.GET(LIVE_COMMON_ACCESS_URL, paramMap, 3000);
			logger.info("forbidLive - " + streamId + " - res : " + res);
			Map<String, Object> resMap = new HashMap<String, Object>();
			resMap = gson.fromJson(res, Map.class);
			// 断流结果:成功
			if (null != resMap.get("ret") && "0.0".equals(resMap.get("ret").toString())) {
				Live update = new Live();
				update.setStreamId(streamId);
				update.setState(EnumLive.STATE_FORBID.getValue());// 设置直播为禁播状态
				liveMapper.updateByPrimaryKeySelective(update);   // 修改&保存直播状态
				return RestResult.OK;
			} else { // 断流结果:失败
				return new RestResult(0, resMap.get("message").toString());
			}
		} catch (IOException e) {
			logger.error("forbidLive - " + streamId + " FAIL");
			return RestResult.FAIL;
		}
	}
}
