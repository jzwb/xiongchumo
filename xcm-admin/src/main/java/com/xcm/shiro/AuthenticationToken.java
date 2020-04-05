package com.xcm.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 登录令牌
 */
public class AuthenticationToken extends UsernamePasswordToken {

	/**
	 * @param username 用户名
	 * @param password 密码
	 */
	public AuthenticationToken(String username, String password) {
		super(username, password);
	}
}