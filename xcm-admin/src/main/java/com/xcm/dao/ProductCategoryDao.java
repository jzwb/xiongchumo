package com.xcm.dao;

import com.xcm.model.ProductCategory;

import java.util.List;

/**
 * Dao - 商品分类
 */
public interface ProductCategoryDao extends BaseDao<ProductCategory, Long> {

    /**
     * 查询商品分类树
     *
     * @return
     */
    List<ProductCategory> findTree();

    /**
     * 查找子级
     * @param productCategory
     * @return
     */
    List<ProductCategory> findChildren(ProductCategory productCategory);

}