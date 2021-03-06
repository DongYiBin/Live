/*
 * Copyright  2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.douxue.live.controller.web.live;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.douxue.live.common.RestResult;
import com.douxue.live.common.page.PageData;
import com.douxue.live.dao.entity.Live;
import com.douxue.live.service.live.LiveService;

/**
 * 后台管理的control层
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
@Controller
@RequestMapping("web/live")
public class WebLiveController {

	@Autowired
	private LiveService liveService;

	@RequestMapping("list")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return "/pages/live/list";
	}

	@RequestMapping("monitoring")
	public ModelAndView monitoring(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("/pages/live/monitoring");
		String streamId = request.getParameter("streamId");
		Live live = null;
		if (null != streamId) {
			live = liveService.getLiveByStreamId(streamId);
		}
		view.addObject("live", live);
		return view;
	}

	@RequestMapping("findLivePageList")
	@ResponseBody
	public RestResult findLivePageList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Live live) {
		PageData<Live> pageData = liveService.findLivePageList(pageNum, pageSize, live);
		return new RestResult(pageData);
	}

	@RequestMapping(value = "forbidLive", method = RequestMethod.POST)
	@ResponseBody
	public RestResult forbidLive(@RequestParam("streamId") String streamId) {
		return liveService.forbidLive(streamId);
	}

	@RequestMapping("getLiveByStreamId")
	@ResponseBody
	public RestResult getLiveByStreamId(@RequestParam("streamId") String streamId) {
		Live live = liveService.getLiveByStreamId(streamId);
		return new RestResult(live);
	}
}
