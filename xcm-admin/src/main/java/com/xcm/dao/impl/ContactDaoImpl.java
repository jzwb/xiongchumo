package com.xcm.dao.impl;

import com.xcm.dao.ContactDao;
import com.xcm.model.Contact;
import org.springframework.stereotype.Repository;

/**
 * Dao - 联系我
 */
@Repository
public class ContactDaoImpl extends BaseDaoImpl<Contact, Long> implements ContactDao {
}