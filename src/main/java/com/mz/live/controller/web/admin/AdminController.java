/*
 * Copyright  2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mz.live.controller.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mz.live.common.RestResult;
import com.mz.live.common.SessionUtils;
import com.mz.live.dao.entity.AdminUser;
import com.mz.live.service.admin.AdminUserService;
import com.mz.live.utils.MD5Utils;

/**
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
@Controller
@RequestMapping("web/admin")
public class AdminController {

	@Autowired
	private AdminUserService adminUserService;

	/**
	 * 登录页
	 * @param request
	 * @param resonse
	 * @return
	 */
	@RequestMapping("loginPage")
	public String loginPage(HttpServletRequest request, HttpServletResponse resonse) {
		return "/pages/login";
	}

	/**
	 * 主界面
	 * @param request
	 * @param resonse
	 * @return
	 */
	@RequestMapping("home")
	public String home(HttpServletRequest request, HttpServletResponse resonse) {
		return "/pages/home";
	}

	/**
	 * 登录
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public RestResult login(@RequestParam("account") String account, @RequestParam("password") String password) {
		AdminUser admin = new AdminUser();
		admin.setAccount(account);
		admin.setPassword(MD5Utils.encryptUserPassword(password));
		admin.setState(0);
		AdminUser loginAdmin = adminUserService.findUser(admin);
		if (null != loginAdmin) {
			SessionUtils.saveAdminUser(loginAdmin);
			return RestResult.OK;
		}
		return RestResult.FAIL;
	}

}
