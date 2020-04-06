package com.xcm.service.impl;

import com.xcm.dao.ProducerDao;
import com.xcm.model.Producer;
import com.xcm.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service - 生厂商
 */
@Service
public class ProducerServiceImpl extends BaseServiceImpl<Producer, Long> implements ProducerService {

    @Autowired
    private ProducerDao producerDao;

    @Autowired
    public void setBaseDao(ProducerDao producerDao) {
        super.setBaseDao(producerDao);
    }
}