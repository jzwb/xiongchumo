package com.xcm.interceptor;

import com.xcm.common.Principal;
import com.xcm.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用户拦截器
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

	private static final String ACCESSDENIED_KEY = "userLoginState";
	private static final String ACCESSDENIED_VALUE = "false";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		HttpSession session = request.getSession();
		Principal principal = (Principal) session.getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
		if (principal != null) {
			return true;
		}
		response.setHeader(ACCESSDENIED_KEY, ACCESSDENIED_VALUE);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
	}
}