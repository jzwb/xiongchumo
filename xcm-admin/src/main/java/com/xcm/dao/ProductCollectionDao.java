package com.xcm.dao;

import com.xcm.model.ProductCollection;
import com.xcm.model.User;

import java.util.List;

/**
 * Dao - 商品收藏
 */
public interface ProductCollectionDao extends BaseDao<ProductCollection, Long> {

    /**
     * 列表
     *
     * @param user 用户
     * @return
     */
    List<Object[]> findList(User user);
}