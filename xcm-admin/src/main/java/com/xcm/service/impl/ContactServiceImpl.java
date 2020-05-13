package com.xcm.service.impl;

import com.xcm.dao.ContactDao;
import com.xcm.model.Contact;
import com.xcm.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service - 联系我
 */
@Service
public class ContactServiceImpl extends BaseServiceImpl<Contact, Long> implements ContactService {

    @Autowired
    private ContactDao contactDao;

    @Autowired
    public void setBaseDao(ContactDao contactDao) {
        super.setBaseDao(contactDao);
    }
}