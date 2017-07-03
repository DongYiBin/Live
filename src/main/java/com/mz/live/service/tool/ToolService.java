package com.mz.live.service.tool;

import com.mz.live.common.RestResult;

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
