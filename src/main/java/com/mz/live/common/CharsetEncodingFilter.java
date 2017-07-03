package com.mz.live.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharsetEncodingFilter implements Filter {
	protected String encoding = null;
	protected FilterConfig filterConfig = null;
	protected boolean ignore = true;

	public CharsetEncodingFilter() {
	}

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain paramFilterChain) throws IOException, ServletException {
		if (!paramServletResponse.isCommitted() && (this.ignore || paramServletRequest.getCharacterEncoding() == null)) {
			String str = this.selectEncoding(paramServletRequest);
			str = str == null ? "utf-8" : str;
			paramServletRequest.setCharacterEncoding(str);
		}

		paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
	}

	public void init(FilterConfig paramFilterConfig) throws ServletException {
		this.filterConfig = paramFilterConfig;
		this.encoding = paramFilterConfig.getInitParameter("encoding");
		String str;
		if ((str = paramFilterConfig.getInitParameter("ignore")) != null && !str.equalsIgnoreCase("true") && !str.equalsIgnoreCase("yes")) {
			this.ignore = false;
		} else {
			this.ignore = true;
		}
	}

	protected String selectEncoding(ServletRequest paramServletRequest) {
		return this.encoding;
	}
}
