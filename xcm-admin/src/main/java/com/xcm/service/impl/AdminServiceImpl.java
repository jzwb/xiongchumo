package com.xcm.service.impl;

import com.xcm.common.Principal;
import com.xcm.dao.AdminDao;
import com.xcm.model.Admin;
import com.xcm.model.Role;
import com.xcm.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Service - 管理员
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long> implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    public void setBaseDao(AdminDao adminDao) {
        super.setBaseDao(adminDao);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean usernameExists(String username) {
        return adminDao.usernameExists(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Admin findByUsername(String username) {
        return adminDao.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAuthorities(Long id) {
        List<String> authorities = new ArrayList<>();
        Admin admin = adminDao.find(id);
        if (admin != null) {
            for (Role role : admin.getRoles()) {
                authorities.addAll(role.getAuthorities());
            }
        }
        return authorities;
    }

    @Override
    @Transactional(readOnly = true)
    public String getCurrentUsername() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            Principal principal = (Principal) subject.getPrincipal();
            if (principal != null) {
                return principal.getUsername();
            }
        }
        return null;
    }

    @Override
    public boolean isLogin() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String loginStatus = (String) request.getSession().getAttribute(Admin.ADMIN_LOGIN_STATUS);
        return Boolean.TRUE.toString().equals(loginStatus);
    }
}