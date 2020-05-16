package com.xcm.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.xcm.dao.ProductCollectionDao;
import com.xcm.model.ProductCollection;
import com.xcm.model.User;
import com.xcm.service.ProductCollectionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service - 商品收藏
 */
@Service
public class ProductCollectionServiceImpl extends BaseServiceImpl<ProductCollection, Long> implements ProductCollectionService {

    @Autowired
    private ProductCollectionDao productCollectionDao;

    @Autowired
    public void setBaseDao(ProductCollectionDao productCollectionDao) {
        super.setBaseDao(productCollectionDao);
    }

    @Override
    public List<Map<String, Object>> findList(User user) {
        if (user == null) {
            return Collections.emptyList();
        }
        List<Object[]> list = productCollectionDao.findList(user);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<Map<String, Object>> newList = new ArrayList<>();
        for (Object[] objects : list) {
            Long id = objects[0] != null ? ((BigInteger) objects[0]).longValue() : null;
            Long productId = objects[1] != null ? ((BigInteger) objects[1]).longValue() : null;
            String productTitle = objects[2] != null ? objects[2].toString() : null;
            String productImagesJson = objects[3] != null ? objects[3].toString() : null;
            Long createDate = objects[4] != null ? ((Timestamp) objects[4]).getTime() : null;
            List<String> productImages = new ArrayList<>();
            try {
                if (StringUtils.isNotBlank(productImagesJson)) {
                    productImages = JSONArray.parseArray(productImagesJson, String.class);
                }
            } catch (Exception ignored) {
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("productId", productId);
            map.put("productTitle", productTitle);
            map.put("productImages", productImages);
            map.put("createDate", createDate);
            newList.add(map);
        }
        return newList;
    }
}