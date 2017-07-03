package com.mz.live.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.mz.live.common.ThreadContext;

public class AuthFilter extends OncePerRequestFilter {
	public AuthFilter() {
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		try {
			ThreadContext.bind();
			// 跨域配置
//			response.addHeader("Access-Control-Allow-Origin", "*");
			chain.doFilter(request, response);
		} finally {
			ThreadContext.unbind();
		}
	}
}
