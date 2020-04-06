package com.xcm.dao.impl;

import com.xcm.dao.ProductCategoryDao;
import com.xcm.model.OrderEntity;
import com.xcm.model.ProductCategory;
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
 * Dao - 商品分类
 */
@Repository
public class ProductCategoryDaoImpl extends BaseDaoImpl<ProductCategory, Long> implements ProductCategoryDao {

    @Override
    public List<ProductCategory> findTree() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductCategory> criteriaQuery = criteriaBuilder.createQuery(ProductCategory.class);
        Root<ProductCategory> root = criteriaQuery.from(ProductCategory.class);
        criteriaQuery.select(root);
        Predicate predicate = criteriaBuilder.conjunction();
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(OrderEntity.ORDER_PROPERTY_NAME)));
        criteriaQuery.where(predicate);
        TypedQuery<ProductCategory> query = entityManager.createQuery(criteriaQuery);
        return sort(query.getResultList(), null);
    }

    @Override
    public List<ProductCategory> findChildren(ProductCategory productCategory) {
        TypedQuery<ProductCategory> query;
        if (productCategory != null) {
            String jpql = "select productCategory from ProductCategory productCategory where productCategory.treePath like :treePath order by productCategory.order asc";
            query = entityManager.createQuery(jpql, ProductCategory.class).setFlushMode(FlushModeType.COMMIT).setParameter("treePath", "%" + productCategory.TREE_PATH_SEPARATOR + productCategory.getId() + productCategory.TREE_PATH_SEPARATOR + "%");
        } else {
            String jpql = "select productCategory from ProductCategory productCategory order by productCategory.order asc";
            query = entityManager.createQuery(jpql, ProductCategory.class).setFlushMode(FlushModeType.COMMIT);
        }
        return sort(query.getResultList(), productCategory);
    }

    @Override
    public void persist(ProductCategory entity) {
        setValue(entity);
        super.persist(entity);
    }

    @Override
    public ProductCategory merge(ProductCategory entity) {
        setValue(entity);
        for (ProductCategory childrenMenu : findChildren(entity)) {
            setValue(childrenMenu);
        }
        return super.merge(entity);
    }

    /**
     * 排序
     *
     * @param productCategories
     * @param parent
     * @return
     */
    private List<ProductCategory> sort(List<ProductCategory> productCategories, ProductCategory parent) {
        List<ProductCategory> result = new ArrayList<>();
        if (productCategories != null) {
            for (ProductCategory productCategory : productCategories) {
                if ((productCategory.getParent() != null && productCategory.getParent().equals(parent)) || (productCategory.getParent() == null && parent == null)) {
                    result.add(productCategory);
                    result.addAll(sort(productCategories, productCategory));
                }
            }
        }
        return result;
    }

    /**
     * 设置值
     *
     * @param productCategory
     */
    private void setValue(ProductCategory productCategory) {
        if (productCategory == null) {
            return;
        }
        ProductCategory parent = productCategory.getParent();
        if (parent != null) {
            productCategory.setTreePath(parent.getTreePath() + parent.getId() + ProductCategory.TREE_PATH_SEPARATOR);
        } else {
            productCategory.setTreePath(ProductCategory.TREE_PATH_SEPARATOR);
        }
        productCategory.setGrade(productCategory.getTreePaths().size());
    }
}