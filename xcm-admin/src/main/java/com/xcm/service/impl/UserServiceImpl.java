package com.xcm.service.impl;

import com.xcm.common.Filter;
import com.xcm.common.Principal;
import com.xcm.dao.UserDao;
import com.xcm.exception.ServiceException;
import com.xcm.model.User;
import com.xcm.service.UserService;
import com.xcm.util.WebUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service - 用户
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    public void setBaseDao(UserDao userDao) {
        super.setBaseDao(userDao);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUnionId(String unionId) {
        if (StringUtils.isBlank(unionId)) {
            return null;
        }
        List<User> users = userDao.findList(null, null, Collections.singletonList(Filter.eq("unionId", unionId)), null);
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        return users.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public void login(User user, boolean isPasswordLogin, String password, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServiceException {
        if (user == null) {
            throw new ServiceException("用户名或密码错误");
        }
        if (isPasswordLogin) {
            if (!DigestUtils.md5Hex(password).equals(user.getPassword())) {
                throw new ServiceException("用户名或密码错误");
            }
        } else {
        }
        Map<String, Object> attributes = new HashMap<>();
        Enumeration<?> keys = session.getAttributeNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            attributes.put(key, session.getAttribute(key));
        }
        session.invalidate();
        session = request.getSession();
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            session.setAttribute(entry.getKey(), entry.getValue());
        }
        session.setAttribute(User.PRINCIPAL_ATTRIBUTE_NAME, new Principal(user.getId(), user.getEmail()));
        WebUtils.addLoginCookies(user, request, response);
    }

    @Override
    @Transactional(readOnly = true)
    public User genRegisterUser(String email, String mobile, String password, HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setEmail(email);
        user.setMobile(mobile);
        user.setPassword(DigestUtils.md5Hex(StringUtils.isBlank(password) ? "123456" : password));
        return user;
    }

    @Override
    public void register(User user, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServiceException {
        if (user == null) {
            throw new ServiceException("注册失败，用户不能为null");
        }
        if (user.getId() == null) {
            userDao.persist(user);
        } else {
            userDao.merge(user);
        }
        Map<String, Object> attributes = new HashMap<>();
        Enumeration<?> keys = session.getAttributeNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            attributes.put(key, session.getAttribute(key));
        }
        session.invalidate();
        session = request.getSession();
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            session.setAttribute(entry.getKey(), entry.getValue());
        }
        session.setAttribute(User.PRINCIPAL_ATTRIBUTE_NAME, new Principal(user.getId(), user.getEmail()));
        WebUtils.addLoginCookies(user, request, response);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) throws ServiceException {
        if (StringUtils.isBlank(email)) {
            return null;
        }
        List<User> users = userDao.findList(null, null, Collections.singletonList(Filter.eq("email", email)), null);
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        if (users.size() > 1) {
            throw new ServiceException("账号异常");
        }
        return users.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByMobile(String mobile) throws ServiceException {
        if (StringUtils.isBlank(mobile)) {
            return null;
        }
        List<User> users = userDao.findList(null, null, Collections.singletonList(Filter.eq("mobile", mobile)), null);
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        if (users.size() > 1) {
            throw new ServiceException("账号异常");
        }
        return users.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean emailExists(String email){
        if(StringUtils.isBlank(email)){
            return false;
        }
        return userService.count(Filter.eq("email", email)) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean mobileExists(String mobile){
        if(StringUtils.isBlank(mobile)){
            return false;
        }
        return userService.count(Filter.eq("mobile", mobile)) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean emailUnique(String oldEmail, String newEmail) {
        if (StringUtils.equalsIgnoreCase(oldEmail, newEmail)) {
            return true;
        }
        return !userService.emailExists(newEmail);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean mobileUnique(String oldMobile, String newMobile) {
        if (StringUtils.equalsIgnoreCase(oldMobile, newMobile)) {
            return true;
        }
        return !userService.mobileExists(newMobile);
    }
}