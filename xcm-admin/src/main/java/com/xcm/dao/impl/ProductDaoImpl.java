package com.xcm.dao.impl;

import com.xcm.dao.ProductDao;
import com.xcm.model.Product;
import org.springframework.stereotype.Repository;

/**
 * Dao - 商品
 */
@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product, Long> implements ProductDao {
}