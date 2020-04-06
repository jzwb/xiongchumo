package com.xcm.service.impl;

import com.xcm.dao.ProductDao;
import com.xcm.model.Product;
import com.xcm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service - 角色
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, Long> implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    public void setBaseDao(ProductDao productDao) {
        super.setBaseDao(productDao);
    }
}