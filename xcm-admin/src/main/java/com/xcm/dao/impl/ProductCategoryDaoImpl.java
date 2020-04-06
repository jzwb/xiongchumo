package com.xcm.dao.impl;

import com.xcm.dao.ProductCategoryDao;
import com.xcm.model.ProductCategory;
import org.springframework.stereotype.Repository;

/**
 * Dao - 商品分类
 */
@Repository
public class ProductCategoryDaoImpl extends BaseDaoImpl<ProductCategory, Long> implements ProductCategoryDao {
}