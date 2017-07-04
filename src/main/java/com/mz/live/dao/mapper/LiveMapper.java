/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.dao.mapper;

import java.util.List;

import com.mz.live.common.page.PageParam;
import com.mz.live.dao.entity.Live;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
public interface LiveMapper {
	int deleteByPrimaryKey(String streamId);

	int insertSelective(Live record);

	Live selectByPrimaryKey(String streamId);

	int updateByPrimaryKeySelective(Live record);

	List<Live> getAppLivePage(PageParam<Live> param);

	List<Live> findLivePageList(PageParam<Live> param);
	
}