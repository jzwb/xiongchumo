package com.xcm.service.impl;

import com.xcm.dao.ProducterDao;
import com.xcm.model.Producter;
import com.xcm.service.ProducterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service - 生厂商
 */
@Service
public class ProducterServiceImpl extends BaseServiceImpl<Producter, Long> implements ProducterService {

    @Autowired
    private ProducterDao producterDao;

    @Autowired
    public void setBaseDao(ProducterDao producterDao) {
        super.setBaseDao(producterDao);
    }
}