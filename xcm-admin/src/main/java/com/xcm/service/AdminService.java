package com.xcm.service;

import com.xcm.model.Admin;

import java.util.List;

/**
 * Service - 管理员
 */
public interface AdminService extends BaseService<Admin, Long> {

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名(忽略大小写)
     * @return
     */
    boolean usernameExists(String username);

    /**
     * 根据用户名查找管理员
     *
     * @param username 用户名(忽略大小写)
     * @return
     */
    Admin findByUsername(String username);

    /**
     * 根据id查找权限
     *
     * @param id
     * @return
     */
    List<String> findAuthorities(Long id);

    /**
     * 获取当前登录用户名
     *
     * @return
     */
    String getCurrentUsername();

    /**
     * 是否登录
     * @return
     */
    boolean isLogin();
}
