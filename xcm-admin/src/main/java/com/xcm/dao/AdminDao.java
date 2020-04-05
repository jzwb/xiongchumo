package com.xcm.dao;

import com.xcm.model.Admin;

/**
 * Dao - 管理员
 */
public interface AdminDao extends BaseDao<Admin, Long> {

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
}