/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.dao.mapper;

import java.util.List;

import com.douxue.live.common.page.PageParam;
import com.douxue.live.dao.entity.User;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
public interface UserMapper {
	int deleteByPrimaryKey(String userId);

	int insertSelective(User record);

	User selectByPrimaryKey(String userId);

	int updateByPrimaryKeySelective(User record);

	User findOneUser(User user);

	List<User> findUserPageList(PageParam<User> param);
}