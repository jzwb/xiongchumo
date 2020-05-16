package com.xcm.service;

import com.xcm.model.BrowsingHistory;
import com.xcm.model.User;

import java.util.List;
import java.util.Map;

/**
 * Service - 浏览历史
 */
public interface BrowsingHistoryService extends BaseService<BrowsingHistory, Long> {

    /**
     * 列表
     *
     * @param pageNumber 页码
     * @param pageSize   页数量
     * @param user       用户
     * @return
     */
    List<Map<String, Object>> findList(Integer pageNumber, Integer pageSize, User user);

    /**
     * 根据用户删除
     *
     * @param user 用户
     */
    void deleteByUser(User user);
}