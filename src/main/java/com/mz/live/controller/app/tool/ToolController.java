package com.mz.live.controller.app.tool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mz.live.common.RestResult;
import com.mz.live.service.tool.ToolService;

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
