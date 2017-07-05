/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.service.user;

import com.douxue.live.common.RestResult;
import com.douxue.live.common.page.PageData;
import com.douxue.live.dao.entity.Live;
import com.douxue.live.dao.entity.User;

/**
 * 用户的service层
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
public interface UserService {

	/**
	 * 用户注册
	 * @param account
	 * @param password
	 * @return
	 */
	RestResult register(String account, String password);

	/**
	 * 用户登录
	 * @param account
	 * @param password
	 * @return
	 */
	RestResult login(String account, String password);

	/**
	 * 查询用户
	 * @param pageNum
	 * @param pageSize
	 * @param user
	 * @return
	 */
	PageData<User> findUserPageList(Integer pageNum, Integer pageSize, User user);

}
