package com.xcm.service.impl;

import com.xcm.dao.MenuDao;
import com.xcm.model.Menu;
import com.xcm.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service - 菜单
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, Long> implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    public void setBaseDao(MenuDao menuDao) {
        super.setBaseDao(menuDao);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findRoots() {
        return menuDao.findRoots();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findTree() {
        return menuDao.findTree();
    }
}