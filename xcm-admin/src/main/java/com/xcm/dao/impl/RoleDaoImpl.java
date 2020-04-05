package com.xcm.dao.impl;

import com.xcm.dao.RoleDao;
import com.xcm.model.Role;
import org.springframework.stereotype.Repository;

/**
 * Dao - 角色
 */
@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role, Long> implements RoleDao {
}