package com.xcm.common;

import java.io.Serializable;

/**
 * 通用 - 身份信息
 */
public class Principal implements Serializable {

	private Long id;
	private String username;//用户名

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 初始化对象
	 *
	 * @param id
	 * @param username 用户名
	 */
	public Principal(Long id, String username) {
		this.id = id;
		this.username = username;
	}

	@Override
	public String toString() {
		return username;
	}
}