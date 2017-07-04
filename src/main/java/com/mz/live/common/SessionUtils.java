/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mz.live.dao.entity.AdminUser;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
public class SessionUtils {

	private static final String ADMIN_USER = "ADMIN_USER";

	// 获得session
	public static final HttpSession getSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(false);
		// 处理session
		if (session == null) {
			session = request.getSession();
		}
		return session;
	}

	// 保存会员到session中
	public static final void saveAdminUser(AdminUser adminUser) {
		getSession().setAttribute(ADMIN_USER, adminUser);
		getSession().setMaxInactiveInterval(1800);
	}

	public static final void removeAdminUser() {
		getSession().removeAttribute(ADMIN_USER);
	}

	public static final AdminUser getAdminUser() {
		Object obj = getSession().getAttribute(ADMIN_USER);
		if (obj != null) {
			AdminUser vo = (AdminUser) obj;
			return vo;
		}
		return null;
	}
}
