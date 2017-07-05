/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douxue.live.dao.entity.AdminUser;
import com.douxue.live.dao.mapper.AdminUserMapper;
import com.douxue.live.service.admin.AdminUserService;

/**
 * adminuser的service层的具体实现
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

	@Autowired
	private AdminUserMapper adminUserMapper;

	/**
	 * 获取admin
	 */
	@Override
	public AdminUser findUser(AdminUser user) {
		return adminUserMapper.findUser(user);
	}

}
