/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.service.user;

import com.mz.live.common.RestResult;

/**
 * 
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

}
