package com.xcm.service;

import com.xcm.exception.ServiceException;
import com.xcm.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Service - 用户
 */
public interface UserService extends BaseService<User, Long> {

    /**
     * 根据unionId查询用户
     *
     * @param unionId
     * @return
     */
    User findByUnionId(String unionId);

    /**
     * 登录
     *
     * @param user            用户
     * @param isPasswordLogin 是否密码登录
     * @param password        密码（密文）
     * @param request
     * @param response
     * @param session
     * @throws ServiceException
     */
    void login(User user, boolean isPasswordLogin, String password, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServiceException;

    /**
     * 生成注册账号（未持久化）
     *
     * @param email
     * @param mobile
     * @param password
     * @param request
     * @param response
     * @return
     */
    User genRegisterUser(String email, String mobile, String password, HttpServletRequest request, HttpServletResponse response);

    /**
     * 注册（自动登录）
     * @param user
     * @param request
     * @param response
     * @param session
     */
    void register(User user, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServiceException;

    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return
     * @throws ServiceException
     */
    User findByEmail(String email) throws ServiceException;

    /**
     * 根据手机查询用户
     * @param mobile 手机
     * @return
     * @throws ServiceException
     */
    User findByMobile(String mobile) throws ServiceException;
}
