/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.dao.mapper;

import java.util.List;

import com.douxue.live.common.page.PageParam;
import com.douxue.live.dao.entity.Live;

/**
 * mybatis中数据库操作接口
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
public interface LiveMapper {
	int deleteByPrimaryKey(String streamId);

	int insertSelective(Live record);

	Live selectByPrimaryKey(String streamId);

	int updateByPrimaryKeySelective(Live record);

	List<Live> getAppLivePage(PageParam<Live> param);

	List<Live> findLivePageList(PageParam<Live> param);
	
}