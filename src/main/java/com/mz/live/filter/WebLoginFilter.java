package com.mz.live.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mz.live.common.SessionUtils;
import com.mz.live.dao.entity.AdminUser;

public class WebLoginFilter implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		AdminUser admin = SessionUtils.getAdminUser();
		if (null != admin) {
			return true;
		}
		// 如果没有登录，且访问是非公开页面，则跳转到登录页面
		response.sendRedirect(request.getContextPath() + "/web/admin/loginPage");
		return true;
	}

}