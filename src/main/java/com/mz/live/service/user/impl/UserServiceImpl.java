/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mz.live.common.RestResult;
import com.mz.live.common.constant.EnumUser;
import com.mz.live.common.constant.ErrorCode;
import com.mz.live.dao.entity.User;
import com.mz.live.dao.mapper.UserMapper;
import com.mz.live.service.user.UserService;
import com.mz.live.utils.MD5Utils;
import com.mz.live.utils.UUIDUtils;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public RestResult register(String account, String password) {
		User user = new User();
		user.setAccount(account);
		User verify = userMapper.findOneUser(user);
		if (null != verify) {
			return new RestResult(ErrorCode.ERROR_ACCOUNT_IS_EXIST);
		}
		user.setUserId(UUIDUtils.getUUID());
		user.setPassword(MD5Utils.encryptUserPassword(password));
		user.setAccountType(EnumUser.ACCOUNT_TYPE_COMMON.getValue());
		// 用户昵称默认为用户账号,可以不设置或者重新定制方案
		user.setNickName(account);
		if(userMapper.insertSelective(user) > 0){
			user = userMapper.findOneUser(user);
			user.setPassword(null);
			user.setWxUnionid(null);
			return new RestResult(user);
		}
		return RestResult.FAIL;
	}

	@Override
	public RestResult login(String account, String password) {
		User query = new User();
		query.setAccount(account);
		query.setPassword(MD5Utils.encryptUserPassword(password));
		User user = userMapper.findOneUser(query);
		if (null == user) {
			return new RestResult(ErrorCode.ERROR_ACCOUNT_OR_PWD);
		}
		if(user.getState().intValue() == EnumUser.STATE_STOP.getValue()){
			return new RestResult(ErrorCode.ERROR_ACCOUNT_STOP);
		}
		user.setPassword(null);
		user.setWxUnionid(null);
		return new RestResult(user);
	}
}
