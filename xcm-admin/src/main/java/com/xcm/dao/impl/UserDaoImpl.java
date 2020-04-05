package com.xcm.dao.impl;

import com.xcm.dao.UserDao;
import com.xcm.model.User;
import org.springframework.stereotype.Repository;

/**
 * Dao - 用户
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {
}