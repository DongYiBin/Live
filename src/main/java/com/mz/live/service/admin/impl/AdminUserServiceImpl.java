package com.mz.live.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mz.live.dao.entity.AdminUser;
import com.mz.live.dao.mapper.AdminUserMapper;
import com.mz.live.service.admin.AdminUserService;

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
