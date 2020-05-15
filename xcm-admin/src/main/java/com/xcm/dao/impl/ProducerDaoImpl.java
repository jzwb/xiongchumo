package com.xcm.dao.impl;

import com.xcm.dao.ProducerDao;
import com.xcm.model.BaseEntity;
import com.xcm.model.OrderEntity;
import com.xcm.model.Producer;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Dao - 生产商
 */
@Repository
public class ProducerDaoImpl extends BaseDaoImpl<Producer, Long> implements ProducerDao {

    @Override
    public List<Producer> findList(Integer pageNumber, Integer pageSize, Producer.Type type, Producer.SortType sortType) {
        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 100;
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Producer> criteriaQuery = criteriaBuilder.createQuery(Producer.class);
        Root<Producer> root = criteriaQuery.from(Producer.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        }
        criteriaQuery.where(restrictions);
        if (Producer.SortType.NEW.equals(sortType)) {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(BaseEntity.CREATE_DATE_PROPERTY_NAME)));
        } else if (Producer.SortType.RECOMMEND.equals(sortType)) {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")), criteriaBuilder.desc(root.get(OrderEntity.ORDER_PROPERTY_NAME)), criteriaBuilder.desc(root.get(BaseEntity.CREATE_DATE_PROPERTY_NAME)));
        } else if (Producer.SortType.HOT.equals(sortType)) {
        }
        return super.findList(criteriaQuery, (pageNumber - 1) * pageSize, pageSize, null, null);
    }
}