package com.xcm.service;

import com.xcm.model.Menu;

import java.util.List;

/**
 * Service - 菜单
 */
public interface MenuService extends BaseService<Menu, Long> {

    /**
     * 查询顶级菜单
     * @return
     */
    List<Menu> findRoots();

    /**
     * 查询素材分类树
     * @return
     */
    List<Menu> findTree();


}
