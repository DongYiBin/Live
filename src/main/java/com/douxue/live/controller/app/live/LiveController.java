/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.controller.app.live;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douxue.live.common.RestResult;
import com.douxue.live.common.page.PageData;
import com.douxue.live.dao.entity.Live;
import com.douxue.live.service.live.LiveService;

/**
 * 手机接口
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
@Controller
@RequestMapping("app/live")
public class LiveController {

	@Autowired
	private LiveService liveService;

	/**
	 * 创建直播
	 * 
	 * @param userId
	 * @param groupId
	 * @param title
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public RestResult create(
			@RequestParam("userId") String userId, @RequestParam("groupId") String groupId, 
			@RequestParam("title") String title,@RequestParam(value = "location", required = false) String location, 
			@RequestParam(value = "coverPic", required = false) String coverPic) {
		Live live = new Live();
		live.setUserId(userId);
		live.setGroupId(groupId);
		live.setTitle(title);
		live.setLocation(location);
		live.setCoverPic(coverPic);
		return liveService.createLive(live);
	}

	/**
	 * 获取直播
	 * 
	 * @param streamId
	 * @return
	 */
	@RequestMapping("getLiveByStreamId")
	@ResponseBody
	public RestResult getLiveByStreamId(@RequestParam("streamId") String streamId) {
		Live live = liveService.getLiveByStreamId(streamId);
		return new RestResult(live);
	}

	/**
	 * 获取直播分页列表
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param live
	 * @return
	 */
	@RequestMapping("getLivePage")
	@ResponseBody
	public RestResult getLivePage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, Live live) {
		PageData<Live> page = liveService.getAppLivePage(pageNo, pageSize, live);
		return new RestResult(page);
	}

	/**
	 * 添加点赞数
	 * @param streamId
	 * @param num
	 * @return
	 * @author zhaodun
	 */
	@RequestMapping(value = "praise/add", method = RequestMethod.POST)
	@ResponseBody
	public RestResult praiseAdd(@RequestParam("streamId") String streamId, @RequestParam(value = "num", defaultValue = "1") Integer num) {
		Live live = liveService.addLivePraiseNum(streamId, num);
		if (null != live) {
			return new RestResult(live);
		}
		return RestResult.FAIL;
	}

	/**
	 * 添加观看数
	 * @param streamId
	 * @param num
	 * @return
	 * @author zhaodun
	 */
	@RequestMapping(value = "viewer/add", method = RequestMethod.POST)
	@ResponseBody
	public RestResult viewerAdd(@RequestParam("streamId") String streamId, @RequestParam(value = "num", defaultValue = "1") Integer num) {
		Live live = liveService.addLiveViewerNum(streamId, num);
		if (null != live) {
			return new RestResult(live);
		}
		return RestResult.FAIL;
	}
	
	/**
	 * 开始直播
	 * @param streamId1
	 * @return
	 * @author zhaodun
	 */
	@RequestMapping(value = "push/start", method = RequestMethod.POST)
	@ResponseBody
	public RestResult pushStart(@RequestParam("streamId") String streamId) {
		Live live = liveService.pushStart(streamId);
		if (null != live) {
			return new RestResult(live);
		}
		return RestResult.FAIL;
	}
	
	/**
	 * 结束直播
	 * @param streamId
	 * @return
	 * @author zhaodun
	 */
	@RequestMapping(value = "push/stop", method = RequestMethod.POST)
	@ResponseBody
	public RestResult pushStop(@RequestParam("streamId") String streamId) {
		Live live = liveService.pushStop(streamId);
		if (null != live) {
			return new RestResult(live);
		}
		return RestResult.FAIL;
	}
}
