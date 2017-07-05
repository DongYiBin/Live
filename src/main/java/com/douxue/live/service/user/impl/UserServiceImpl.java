/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douxue.live.common.RestResult;
import com.douxue.live.common.constant.EnumUser;
import com.douxue.live.common.constant.ErrorCode;
import com.douxue.live.common.page.PageData;
import com.douxue.live.common.page.PageParam;
import com.douxue.live.dao.entity.Live;
import com.douxue.live.dao.entity.User;
import com.douxue.live.dao.mapper.UserMapper;
import com.douxue.live.service.user.UserService;
import com.douxue.live.utils.MD5Utils;
import com.douxue.live.utils.UUIDUtils;

/**
 * 用户的service层具体实现
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 用户注册
	 */
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

	/**
	 * 用户登录
	 */
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

	@Override
	public PageData<User> findUserPageList(Integer pageNum, Integer pageSize, User user) {
		PageParam<User> param = new PageParam<User>(user, pageNum, pageSize);
		List<User> data = userMapper.findLivePageList(param);
		PageData<User> page = new PageData<User>(pageNum, pageSize, param.getDataTotal(), data);
		return page;
	}
}
