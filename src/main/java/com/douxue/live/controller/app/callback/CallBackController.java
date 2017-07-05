/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.controller.app.callback;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douxue.live.controller.app.callback.vo.LiveCalbackVo;
import com.douxue.live.service.live.LiveService;
import com.douxue.live.service.live.impl.LiveApiTool;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 手机接接口
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
@Controller
@RequestMapping("app/callback")
public class CallBackController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private Gson gson = new GsonBuilder().create();

	@Autowired
	private LiveApiTool liveApiTool;
	@Autowired
	private LiveService liveService;

	/**
	 * 直播相关回调
	 * @param callback
	 * @return
	 * @author zhaodun
	 */
	@RequestMapping("live/inform")
	@ResponseBody
	public String liveInform(@RequestBody String callback) {
		logger.info(callback);
		// JSON 转换 对象实体
		LiveCalbackVo cv = gson.fromJson(callback, LiveCalbackVo.class);
		// 签名验证 : 失败返回消息
		if (!liveApiTool.callbackSignAuth(cv.getSign(), cv.getT())) {
			logger.info("callbackSignAuth - FAIL ");
		} else {
			// 签名验证 : 成功
			logger.info("callbackSignAuth - SUCCESS ");
			// 回调业务逻辑处理
			if (liveService.liveCallBackDispose(cv)) {
				logger.info("liveCallBackDispose - SUCCESS : " + "event_type=" + cv.getEvent_type() + "  stream_id=" + cv.getStream_id());
				Map<String, Object> resMap = new HashMap<String, Object>();
				// 回调消息响应
				resMap.put("code", 0);
				String res = gson.toJson(resMap);
				return res;
			} else {
				logger.info("liveCallBackDispose - FAIL : " + "event_type=" + cv.getEvent_type() + "  stream_id=" + cv.getStream_id());
			}
		}
		return "";
	}
}
