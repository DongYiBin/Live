/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.service.tool;

import com.mz.live.common.RestResult;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
public interface ToolService {

	/**
	 * 腾讯云存储Sign
	 * @param type 0:查询签名,1,下载签名
	 * @param isSingle 
	 * @return RestResult
	 * @author zhaodun
	 */
	public RestResult getQCloudCosSign(int type, boolean isSingle);

}
