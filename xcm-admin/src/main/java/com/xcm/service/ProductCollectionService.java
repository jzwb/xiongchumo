package com.xcm.service;

import com.xcm.model.ProductCollection;
import com.xcm.model.User;

import java.util.List;
import java.util.Map;

/**
 * Service - 商品收藏
 */
public interface ProductCollectionService extends BaseService<ProductCollection, Long> {

    /**
     * 列表
     *
     * @param user 用户
     * @return
     */
    List<Map<String, Object>> findList(User user);
}