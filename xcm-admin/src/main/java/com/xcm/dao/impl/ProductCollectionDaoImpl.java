package com.xcm.dao.impl;

import com.xcm.dao.ProductCollectionDao;
import com.xcm.model.ProductCollection;
import com.xcm.model.User;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Dao - 商品收藏
 */
@Repository
public class ProductCollectionDaoImpl extends BaseDaoImpl<ProductCollection, Long> implements ProductCollectionDao {

    @Override
    public List<Object[]> findList(User user) {
        if (user == null) {
            return Collections.emptyList();
        }
        String sql = "SELECT tpc.id AS c1, tp.id As c2, tp.title, tp.images_json, tpc.create_date FROM t_product_collection tpc LEFT JOIN t_product tp ON tpc.product = tp.id WHERE 1 = 1 AND tpc.user = :user ORDER BY tpc.create_date DESC";
        return entityManager.createNativeQuery(sql).setParameter("user", user.getId()).getResultList();
    }
}