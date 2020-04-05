package com.xcm.converter;

import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * 转换器 - 枚举
 */
public class EnumConverter extends AbstractConverter {

	private final Class<?> enumClass;//枚举类型

	/**
	 * 初始化对象
	 *
	 * @param enumClass 枚举类型
	 */
	public EnumConverter(Class<?> enumClass) {
		this(enumClass, null);
	}

	/**
	 * 初始化对象
	 *
	 * @param enumClass    枚举类型
	 * @param defaultValue 默认值
	 */
	public EnumConverter(Class<?> enumClass, Object defaultValue) {
		super(defaultValue);
		this.enumClass = enumClass;
	}

	/**
	 * 获取默认类型
	 *
	 * @return
	 */
	@Override
	protected Class<?> getDefaultType() {
		return this.enumClass;
	}

	/**
	 * 转换为枚举对象
	 *
	 * @param type  类型
	 * @param value 值
	 * @return
	 */
	protected Object convertToType(Class type, Object value) {
		String stringValue = value.toString().trim();
		return Enum.valueOf(type, stringValue);
	}

	/**
	 * 转换为字符串
	 *
	 * @param value 值
	 * @return
	 */
	protected String convertToString(Object value) {
		return value.toString();
	}
}