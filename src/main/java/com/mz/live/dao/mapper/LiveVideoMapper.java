package com.mz.live.dao.mapper;

import com.mz.live.dao.entity.LiveVideo;

public interface LiveVideoMapper {
    int deleteByPrimaryKey(Long lvId);

    int insertSelective(LiveVideo record);

    LiveVideo selectByPrimaryKey(Long lvId);

    int updateByPrimaryKeySelective(LiveVideo record);
}