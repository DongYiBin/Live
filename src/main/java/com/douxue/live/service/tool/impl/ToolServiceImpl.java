/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.service.tool.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.douxue.live.common.RestResult;
import com.douxue.live.service.tool.ToolService;
import com.qcloud.cos.exception.AbstractCosException;
import com.qcloud.cos.sign.Credentials;
import com.qcloud.cos.sign.Sign;

/**
 * 腾讯云云储存service层具体实现
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
@Service
public class ToolServiceImpl implements ToolService {

	/** 签名有效时间 30天 */
	private final static long COS_SIGN_TIME_OUT = 60 * 60 * 24 * 30;
	
	// 腾讯云存储
	@Value("${qcloud_cos_secret_id}")
	private String QQ_CLOUD_COS_SECRET_ID;
	@Value("${qcloud_cos_secret_key}")
	private String QQ_CLOUD_COS_SECRET_KEY;
	@Value("${qcloud_cos_app_id}")
	private String QQ_CLOUD_COS_APP_ID;
	@Value("${qcloud_cos_bucket}")
	private String QQ_CLOUD_COS_BUCKET;

	/**
	 * 腾讯云存储Sign
	 * @param type 0:查询签名,1,下载签名
	 * @param isSingle 
	 * @return RestResult
	 * @author zhaodun
	 */
	@Override
	public RestResult getQCloudCosSign(int type, boolean isSingle) {
		Credentials cred = new Credentials(Integer.parseInt(QQ_CLOUD_COS_APP_ID), QQ_CLOUD_COS_SECRET_ID, QQ_CLOUD_COS_SECRET_KEY);
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			if (type == 0) {
				if (isSingle) {
					resMap.put("cosSign", Sign.getOneEffectiveSign(QQ_CLOUD_COS_BUCKET, "/", cred));
					return new RestResult(resMap);
				} else {
					long expired = System.currentTimeMillis() / 1000 + COS_SIGN_TIME_OUT;
					resMap.put("cosSign", Sign.getPeriodEffectiveSign(QQ_CLOUD_COS_BUCKET, "/", cred, expired));
					return new RestResult(resMap);
				}
			} else {
				// 下载签名
				long expired = System.currentTimeMillis() / 1000 + COS_SIGN_TIME_OUT;
				resMap.put("cosSign", Sign.getDownLoadSign(QQ_CLOUD_COS_BUCKET, "/", cred, expired));
				return new RestResult(resMap);
			}
		} catch (AbstractCosException e) {
			e.printStackTrace();
		}
		return RestResult.FAIL;
	}
}
