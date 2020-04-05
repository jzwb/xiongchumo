package com.xcm.dao.impl;

import com.xcm.dao.AdminDao;
import com.xcm.model.Admin;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

/**
 * Dao - 管理员
 */
@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin, Long> implements AdminDao {

    @Override
    public boolean usernameExists(String username) {
        if (username == null){
            return false;
        }
        String jpql = "SELECT COUNT(*) FROM Admin admin WHERE admin.username = :username";
        Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
        return count > 0;
    }

    @Override
    public Admin findByUsername(String username) {
        if (username == null){
            return null;
        }
        try {
            String jpql = "SELECT admin FROM Admin admin WHERE admin.username = :username";
            return entityManager.createQuery(jpql, Admin.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}