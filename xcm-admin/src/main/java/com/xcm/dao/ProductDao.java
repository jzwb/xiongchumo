package com.xcm.dao;

import com.xcm.model.Producer;
import com.xcm.model.Product;

import java.util.List;

/**
 * Dao - 商品
 */
public interface ProductDao extends BaseDao<Product, Long> {

    /**
     * 列表
     *
     * @param pageNumber 页码
     * @param pageSize   页数量
     * @param type       生产商类型
     * @param sortType   排序类型
     * @return
     */
    List<Product> findList(Integer pageNumber, Integer pageSize, Producer.Type type, Product.SortType sortType);

    /**
     * 列表
     *
     * @param pageNumber 页码
     * @param pageSize   页数量
     * @param producer   生产商
     * @param sortType   排序类型
     * @return
     */
    List<Product> findList(Integer pageNumber, Integer pageSize, Producer producer, Product.SortType sortType);
}