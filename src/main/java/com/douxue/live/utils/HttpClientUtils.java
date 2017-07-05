/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.utils;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
public class HttpClientUtils {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static String GET(String url, int timeOut) throws IOException {
		HttpClient client = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setContentCharset("UTF-8");
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeOut);
		client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
		client.executeMethod(getMethod);
		return getMethod.getResponseBodyAsString();
	}

	public static String GET(String url, LinkedHashMap<String, Object> params, int timeOut) throws IOException {
		HttpClient client = new HttpClient();
		if (null != params) {
			url = jointUrl(url, params);
		}
		System.out.println(url);
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setContentCharset("UTF-8");
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeOut);
		client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
		client.executeMethod(getMethod);
		return getMethod.getResponseBodyAsString();
	}

	public static String POST(String url, LinkedHashMap<String, Object> headers, LinkedHashMap<String, Object> params, int timeOut) throws IOException {
		HttpClient client = new HttpClient();
		PostMethod httpPost = new PostMethod(url);
		if (null != headers && headers.size() > 0) {
			for (String key : headers.keySet()) {
				httpPost.addRequestHeader(key, headers.get(key).toString());
			}
		}
		if (null != params && params.size() > 0) {
			for (String key : params.keySet()) {
				if (null != params.get(key)) {
					httpPost.addParameter(key, params.get(key).toString());
				}
			}
		}
		httpPost.getParams().setContentCharset("utf-8");
		httpPost.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeOut);
		client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
		client.executeMethod(httpPost);
		return httpPost.getResponseBodyAsString();
	}

	public static String jointUrl(String url, LinkedHashMap<String, Object> params) {
		String tem = "";
		for (String key : params.keySet()) {
			tem += "&" + key + "=" + params.get(key).toString();
		}
		if (!"".equals(tem)) {
			url += "?" + tem.substring(1);
		}
		return url;
	}
}
