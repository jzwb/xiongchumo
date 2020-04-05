package com.xcm.dao.impl;

import com.xcm.dao.MenuDao;
import com.xcm.model.Menu;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao - 菜单
 */
@Repository
public class MenuDaoImpl extends BaseDaoImpl<Menu, Long> implements MenuDao {

    @Override
    public List<Menu> findRoots() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Menu> criteriaQuery = criteriaBuilder.createQuery(Menu.class);
        Root<Menu> root = criteriaQuery.from(Menu.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("parent")));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")));
        criteriaQuery.where(restrictions);
        TypedQuery<Menu> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<Menu> findTree() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Menu> criteriaQuery = criteriaBuilder.createQuery(Menu.class);
        Root<Menu> root = criteriaQuery.from(Menu.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")));
        criteriaQuery.where(restrictions);
        TypedQuery<Menu> query = entityManager.createQuery(criteriaQuery);
        return sort(query.getResultList(), null);
    }

    @Override
    public List<Menu> findChildren(Menu menu) {
        TypedQuery<Menu> query;
        if (menu != null) {
            String jpql = "select menu from Menu menu where menu.treePath like :treePath order by menu.order asc";
            query = entityManager.createQuery(jpql, Menu.class).setFlushMode(FlushModeType.COMMIT).setParameter("treePath", "%" + Menu.TREE_PATH_SEPARATOR + menu.getId() + Menu.TREE_PATH_SEPARATOR + "%");
        } else {
            String jpql = "select menu from Menu menu order by menu.order asc";
            query = entityManager.createQuery(jpql, Menu.class).setFlushMode(FlushModeType.COMMIT);
        }
        return sort(query.getResultList(), menu);
    }

    @Override
    public void persist(Menu entity) {
        setValue(entity);
        super.persist(entity);
    }

    @Override
    public Menu merge(Menu entity) {
        setValue(entity);
        for (Menu childrenMenu : findChildren(entity)) {
            setValue(childrenMenu);
        }
        return super.merge(entity);
    }

    /**
     * 排序
     *
     * @param menus
     * @param parent
     * @return
     */
    private List<Menu> sort(List<Menu> menus, Menu parent) {
        List<Menu> result = new ArrayList<>();
        if (menus != null) {
            for (Menu menu : menus) {
                if ((menu.getParent() != null && menu.getParent().equals(parent)) || (menu.getParent() == null && parent == null)) {
                    result.add(menu);
                    result.addAll(sort(menus, menu));
                }
            }
        }
        return result;
    }

    /**
     * 设置值
     *
     * @param menu
     */
    private void setValue(Menu menu) {
        if (menu == null){
            return;
        }
        Menu parent = menu.getParent();
        if (parent != null) {
            menu.setTreePath(parent.getTreePath() + parent.getId() + Menu.TREE_PATH_SEPARATOR);
        } else {
            menu.setTreePath(Menu.TREE_PATH_SEPARATOR);
        }
        menu.setGrade(menu.getTreePaths().size());
    }
}