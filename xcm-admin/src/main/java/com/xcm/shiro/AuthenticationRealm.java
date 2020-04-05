package com.xcm.shiro;

import com.xcm.common.Principal;
import com.xcm.model.Admin;
import com.xcm.service.AdminService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 权限认证
 */
public class AuthenticationRealm extends AuthorizingRealm {

	@Autowired
	private AdminService adminService;

	/**
	 * 获取认证信息
	 *
	 * @param token 令牌
	 * @return 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token) {
		AuthenticationToken authenticationToken = (AuthenticationToken) token;
		String username = authenticationToken.getUsername();
		String password = new String(authenticationToken.getPassword());
		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			Admin admin = adminService.findByUsername(username);
			if (admin == null) {
				throw new UnknownAccountException();
			}
			if (!DigestUtils.md5Hex(password).equals(admin.getPassword())) {
				throw new IncorrectCredentialsException();
			}
			return new SimpleAuthenticationInfo(new Principal(admin.getId(), admin.getUsername()), password, getName());
		}
		throw new UnknownAccountException();
	}

	/**
	 * 获取授权信息
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
		if (principal != null) {
			List<String> authorities = adminService.findAuthorities(principal.getId());
			if (authorities != null) {
				SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
				authorizationInfo.addStringPermissions(authorities);
				return authorizationInfo;
			}
		}
		return null;
	}
}