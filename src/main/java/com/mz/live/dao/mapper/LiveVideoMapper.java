/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.dao.mapper;

import com.mz.live.dao.entity.LiveVideo;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
public interface LiveVideoMapper {
    int deleteByPrimaryKey(Long lvId);

    int insertSelective(LiveVideo record);

    LiveVideo selectByPrimaryKey(Long lvId);

    int updateByPrimaryKeySelective(LiveVideo record);
}