package com.mz.live.service.user;

import com.mz.live.common.RestResult;

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
