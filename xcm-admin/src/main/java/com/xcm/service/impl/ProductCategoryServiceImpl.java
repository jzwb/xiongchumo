package com.xcm.service.impl;

import com.xcm.dao.ProductCategoryDao;
import com.xcm.model.ProductCategory;
import com.xcm.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service - 商品分类
 */
@Service
public class ProductCategoryServiceImpl extends BaseServiceImpl<ProductCategory, Long> implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    public void setBaseDao(ProductCategoryDao productCategoryDao) {
        super.setBaseDao(productCategoryDao);
    }
}