/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.controller.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douxue.live.common.RestResult;
import com.douxue.live.service.user.UserService;

/**
 * 手机接口
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
@Controller
@RequestMapping("app/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 用户注册
	 * @param account
	 * @param password
	 * @return
	 * @author zhaodun
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	public RestResult register(@RequestParam("account") String account, 
			@RequestParam("password") String password) {
		return userService.register(account, password);
	}

	/**
	 * 用户登录
	 * @param account
	 * @param password
	 * @return
	 * @author zhaodun
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public RestResult login(@RequestParam("account") String account, 
			@RequestParam("password") String password) {
		return userService.login(account, password);
	}
}
