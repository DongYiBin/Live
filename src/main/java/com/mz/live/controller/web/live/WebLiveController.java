package com.mz.live.controller.web.live;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mz.live.common.RestResult;
import com.mz.live.common.page.PageData;
import com.mz.live.dao.entity.Live;
import com.mz.live.service.live.LiveService;

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