package com.xcm.interceptor;

import com.xcm.model.Log;
import com.xcm.service.AdminService;
import com.xcm.service.LogService;
import com.xcm.util.Tools;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Interceptor - 日志记录
 */
@Component
public class LogInterceptor extends HandlerInterceptorAdapter {

	private static final String[] DEFAULT_IGNORE_PARAMETERS = new String[]{"password", "rePassword"};//默认忽略参数

	@Autowired
	private AdminService adminService;
	@Autowired
	private LogService logService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();
		String operator = adminService.getCurrentUsername();
		String ip = Tools.getIp(request);
		Map<String, String[]> parameterMap = request.getParameterMap();
		StringBuilder parameter = new StringBuilder();
		if (parameterMap != null) {
			for (Entry<String, String[]> entry : parameterMap.entrySet()) {
				String parameterName = entry.getKey();
				if (!ArrayUtils.contains(DEFAULT_IGNORE_PARAMETERS, parameterName)) {
					String[] parameterValues = entry.getValue();
					if (parameterValues != null) {
						for (String parameterValue : parameterValues) {
							parameter.append(parameterName).append(" = ").append(parameterValue).append("\n");
						}
					}
				}
			}
		}
		Log log = new Log();
		log.setOperator(operator);
		log.setOperation(path);
		log.setParameter(parameter.toString());
		if (parameter.toString().length() < 100) {
			log.setContent(parameter.toString());
		}
		log.setIp(ip);
		logService.save(log);
		return true;
	}
}