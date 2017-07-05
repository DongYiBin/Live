/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.dao.mapper;

import com.douxue.live.dao.entity.LiveVideo;

/**
 * mybatis中数据库操作接口
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
public interface LiveVideoMapper {
    int deleteByPrimaryKey(Long lvId);

    int insertSelective(LiveVideo record);

    LiveVideo selectByPrimaryKey(Long lvId);

    int updateByPrimaryKeySelective(LiveVideo record);
}