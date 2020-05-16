package com.xcm.dao;

import com.xcm.model.BrowsingHistory;
import com.xcm.model.User;

/**
 * Dao - 浏览历史
 */
public interface BrowsingHistoryDao extends BaseDao<BrowsingHistory, Long> {

    /**
     * 根据用户删除
     *
     * @param user 用户
     */
    void deleteByUser(User user);
}