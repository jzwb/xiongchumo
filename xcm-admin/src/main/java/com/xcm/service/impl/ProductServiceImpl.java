package com.xcm.service.impl;

import com.xcm.controller.api.ProductController;
import com.xcm.dao.ProductDao;
import com.xcm.model.Producer;
import com.xcm.model.Product;
import com.xcm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

    @Override
    public List<Product> findList(Integer pageNumber, Integer pageSize, Producer.Type type, ProductController.SortType sortType) {
        return productDao.findList(pageNumber, pageSize, type, sortType);
    }
}