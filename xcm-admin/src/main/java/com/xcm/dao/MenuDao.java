package com.xcm.dao;

import com.xcm.model.Menu;

import java.util.List;

/**
 * Dao - 菜单
 */
public interface MenuDao extends BaseDao<Menu, Long> {

    /**
     * 查询顶级菜单
     * @return
     */
    List<Menu> findRoots();

    /**
     * 查询分类树
     * @return
     */
    List<Menu> findTree();

    /**
     * 查找子级
     * @param category
     * @return
     */
    List<Menu> findChildren(Menu category);
}