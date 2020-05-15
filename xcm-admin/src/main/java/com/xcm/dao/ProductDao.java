package com.xcm.dao;

import com.xcm.controller.api.ProductController;
import com.xcm.model.Producer;
import com.xcm.model.Product;

import java.util.List;

/**
 * Dao - 商品
 */
public interface ProductDao extends BaseDao<Product, Long> {

    /**
     * 置顶列表
     *
     * @param pageNumber 页码
     * @param pageSize   页数量
     * @param type       生厂商类型
     * @param sortType   排序类型
     * @return
     */
    List<Product> findList(Integer pageNumber, Integer pageSize, Producer.Type type, ProductController.SortType sortType);
}