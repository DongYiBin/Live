package com.mz.live.dao.mapper;

import java.util.List;

import com.mz.live.common.page.PageParam;
import com.mz.live.dao.entity.Live;

public interface LiveMapper {
	int deleteByPrimaryKey(String streamId);

	int insertSelective(Live record);

	Live selectByPrimaryKey(String streamId);

	int updateByPrimaryKeySelective(Live record);

	List<Live> getAppLivePage(PageParam<Live> param);

	List<Live> findLivePageList(PageParam<Live> param);
	
}