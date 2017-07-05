/*
 * Copyright  2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.douxue.live.controller.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douxue.live.common.RestResult;
import com.douxue.live.common.SessionUtils;
import com.douxue.live.common.page.PageData;
import com.douxue.live.dao.entity.AdminUser;
import com.douxue.live.dao.entity.User;
import com.douxue.live.service.admin.AdminUserService;
import com.douxue.live.service.user.UserService;
import com.douxue.live.utils.MD5Utils;


/**
 * admin的control层
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
@Controller
@RequestMapping("web/admin")
public class AdminController {

	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private UserService userService;

	@RequestMapping("loginPage")
	public String loginPage(HttpServletRequest request, HttpServletResponse resonse) {
		return "/pages/login";
	}

	@RequestMapping("home")
	public String home(HttpServletRequest request, HttpServletResponse resonse) {
		return "/pages/home";
	}

	@RequestMapping("muser")
	public String mUser(HttpServletRequest request, HttpServletResponse response) {
		return "/pages/live/muser";
	}
	
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

	@RequestMapping("findUserPageList")
	@ResponseBody
	public RestResult findUserPageList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, User user) {
		PageData<User> pageData = userService.findUserPageList(pageNum, pageSize, user);
		return new RestResult(pageData);
	}
	
	/**
	 * 删除账户
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "delaccount", method = RequestMethod.POST)
	@ResponseBody
	public RestResult delAcccount(@RequestParam("userId") String userId) {
		int key = userService.deleteByPrimaryKey(userId);
		if(key > 0){
			return RestResult.OK;
		}
		return RestResult.FAIL;
	}
	
	/**
	 * 启/禁用账户
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "disaccount", method = RequestMethod.POST)
	@ResponseBody
	public RestResult disAcccount(@RequestParam("userId") String userId, @RequestParam("state") int state) {
		User record = new User();
		record.setUserId(userId);
		if (record != null) {
			record.setState(state); // 禁用状态
			int key = userService.updateByPrimaryKeySelective(record);
			if (key > 0) {
				return RestResult.OK;
			}
		}
		return RestResult.FAIL;
	}
}