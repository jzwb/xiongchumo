package com.xcm.filter;

import com.xcm.model.User;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter - cookie
 */
public class CookieFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		autoLogin(request, response);
		filterChain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * 自动登录
	 * @param request
	 * @param response
	 */
	private void autoLogin(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session.getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME) != null) {
			return;
		}
	}

	@Override
	public void destroy() {

	}
}
