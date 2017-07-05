/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.controller.app.tool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douxue.live.common.RestResult;
import com.douxue.live.service.tool.ToolService;

/**
 * 手机接口
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
@Controller
@RequestMapping("app/tool")
public class ToolController {

	@Autowired
	private ToolService toolService;

	/**
	 * 腾讯云存储
	 * 
	 * @param type 0:上传/查询签名,1.下载签名
	 * @param isSingle
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ClientProtocolException
	 * @throws IOException
	 *             RestResult
	 * @author zhaodun
	 */
	@RequestMapping("getQCloudSign")
	@ResponseBody
	public RestResult getQCloudSign(@RequestParam(value = "type", defaultValue = "0") Integer type,
			@RequestParam(value = "isSingle", defaultValue = "false") boolean isSingle) throws UnsupportedEncodingException, ClientProtocolException, IOException {
		return toolService.getQCloudCosSign(type, isSingle);
	}
}
