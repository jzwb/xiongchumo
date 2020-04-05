package com.xcm.service.impl;

import com.xcm.dao.RoleDao;
import com.xcm.model.Role;
import com.xcm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service - 角色
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    public void setBaseDao(RoleDao roleDao) {
        super.setBaseDao(roleDao);
    }
}