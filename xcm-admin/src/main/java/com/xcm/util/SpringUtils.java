package com.xcm.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Utils - Spring
 */
@Component
@Lazy(false)
public final class SpringUtils implements ApplicationContextAware, DisposableBean {

	/**
	 * applicationContext
	 */
	private static ApplicationContext applicationContext;

	/**
	 * 不可实例化
	 */
	private SpringUtils() {
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringUtils.applicationContext = applicationContext;
	}

	public void destroy() {
		applicationContext = null;
	}

	/**
	 * 获取applicationContext
	 *
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取实例
	 *
	 * @param name Bean名称
	 * @return
	 */
	public static Object getBean(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		return applicationContext.getBean(name);
	}

	/**
	 * 获取实例
	 *
	 * @param name Bean名称
	 * @param type Bean类型
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> type) {
		return applicationContext.getBean(name, type);
	}
}