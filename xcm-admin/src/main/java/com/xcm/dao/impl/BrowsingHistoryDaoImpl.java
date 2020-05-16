package com.xcm.dao.impl;

import com.xcm.dao.BrowsingHistoryDao;
import com.xcm.model.BrowsingHistory;
import com.xcm.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;

/**
 * Dao - 浏览历史
 */
@Repository
public class BrowsingHistoryDaoImpl extends BaseDaoImpl<BrowsingHistory, Long> implements BrowsingHistoryDao {

    @Override
    public void deleteByUser(User user) {
        if (user == null) {
            return;
        }
        String jpa = "DELETE FROM BrowsingHistory WHERE user = :user";
        entityManager.createQuery(jpa).setParameter("user", user.getId()).setFlushMode(FlushModeType.COMMIT).executeUpdate();
    }
}