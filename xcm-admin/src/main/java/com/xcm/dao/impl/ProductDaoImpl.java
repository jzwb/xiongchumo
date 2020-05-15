package com.xcm.dao.impl;

import com.xcm.controller.api.ProductController;
import com.xcm.dao.ProductDao;
import com.xcm.model.BaseEntity;
import com.xcm.model.OrderEntity;
import com.xcm.model.Producer;
import com.xcm.model.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Dao - 商品
 */
@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product, Long> implements ProductDao {

    @Override
    public List<Product> findList(Integer pageNumber, Integer pageSize, Producer.Type type, ProductController.SortType sortType) {
        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 100;
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("producer").get("type"), type));
        }
        criteriaQuery.where(restrictions);
        if (ProductController.SortType.NEW.equals(sortType)) {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(BaseEntity.CREATE_DATE_PROPERTY_NAME)));
        } else if (ProductController.SortType.RECOMMEND.equals(sortType)) {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")), criteriaBuilder.desc(root.get(OrderEntity.ORDER_PROPERTY_NAME)), criteriaBuilder.desc(root.get(BaseEntity.CREATE_DATE_PROPERTY_NAME)));
        } else if (ProductController.SortType.HOT.equals(sortType)) {

        }
        return super.findList(criteriaQuery, (pageNumber - 1) * pageSize, pageSize, null, null);
    }
}