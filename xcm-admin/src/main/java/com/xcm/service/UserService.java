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
     *
     * @param user
     * @param request
     * @param response
     * @param session
     */
    void register(User user, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServiceException;

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return
     * @throws ServiceException
     */
    User findByEmail(String email) throws ServiceException;

    /**
     * 根据手机查询用户
     *
     * @param mobile 手机
     * @return
     * @throws ServiceException
     */
    User findByMobile(String mobile) throws ServiceException;

    /**
     * 邮箱是否存在
     *
     * @param email 邮箱
     * @return
     */
    boolean emailExists(String email);

    /**
     * 手机是否存在
     *
     * @param mobile 手机
     * @return
     */
    boolean mobileExists(String mobile);

    /**
     * 昵称是否存在
     *
     * @param nickName 昵称
     * @return
     */
    boolean nickNameExists(String nickName);

    /**
     * 邮箱是否唯一
     *
     * @param oldEmail 旧邮箱
     * @param newEmail 新邮箱
     * @return
     */
    boolean emailUnique(String oldEmail, String newEmail);

    /**
     * 手机是否唯一
     *
     * @param oldMobile 旧手机
     * @param newMobile 新手机
     * @return
     */
    boolean mobileUnique(String oldMobile, String newMobile);

    /**
     * 昵称是否唯一
     *
     * @param oldNickName 旧昵称
     * @param newNickName 新昵称
     * @return
     */
    boolean nickNameUnique(String oldNickName, String newNickName);

    /**
     * 获取当前登陆用户
     *
     * @return
     */
    User getCurrent();
}