package com.xcm.service;

import com.xcm.model.ProductCategory;

import java.util.List;

/**
 * Service - 商品分类
 */
public interface ProductCategoryService extends BaseService<ProductCategory, Long> {

    /**
     * 查询商品分类树
     *
     * @return
     */
    List<ProductCategory> findTree();
}