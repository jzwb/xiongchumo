package com.xcm.service.impl;

import com.xcm.common.Filter;
import com.xcm.common.Order;
import com.xcm.dao.BrowsingHistoryDao;
import com.xcm.model.BaseEntity;
import com.xcm.model.BrowsingHistory;
import com.xcm.model.Producer;
import com.xcm.model.Product;
import com.xcm.model.User;
import com.xcm.service.BrowsingHistoryService;
import com.xcm.service.ProducerService;
import com.xcm.service.ProductService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service - 浏览历史
 */
@Service
public class BrowsingHistoryServiceImpl extends BaseServiceImpl<BrowsingHistory, Long> implements BrowsingHistoryService {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProducerService producerService;

    @Autowired
    private BrowsingHistoryDao browsingHistoryDao;

    @Autowired
    public void setBaseDao(BrowsingHistoryDao browsingHistoryDao) {
        super.setBaseDao(browsingHistoryDao);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> findList(Integer pageNumber, Integer pageSize, User user) {
        if (user == null) {
            return Collections.emptyList();
        }
        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 50;
        }
        List<BrowsingHistory> list = browsingHistoryDao.findList((pageNumber - 1) * pageSize, pageSize, Collections.singletonList(Filter.eq("user", user.getId())), Collections.singletonList(Order.desc(BaseEntity.CREATE_DATE_PROPERTY_NAME)));
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        Map<String, Map<String, Object>> data = new LinkedHashMap<>();
        List<Long> productIds = new ArrayList<>();
        List<Long> producerIds = new ArrayList<>();
        for (BrowsingHistory browsingHistory : list) {
            if (BrowsingHistory.Type.product.equals(browsingHistory.getType())) {
                productIds.add(browsingHistory.getRelId());
            } else if (BrowsingHistory.Type.producer.equals(browsingHistory.getType())) {
                producerIds.add(browsingHistory.getRelId());
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id", browsingHistory.getId());
            map.put("type", browsingHistory.getType());
            map.put("createDate", browsingHistory.getCreateDate().getTime());
            data.put(browsingHistory.getType().toString() + browsingHistory.getRelId(), map);
        }
        List<Product> products = productService.findList(productIds.toArray(new Long[0]));
        List<Producer> producers = producerService.findList(producerIds.toArray(new Long[0]));
        if (CollectionUtils.isNotEmpty(products)) {
            for (Product product : products) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", product.getId());
                map.put("title", product.getTitle());
                map.put("images", product.getImages());
                String key = BrowsingHistory.Type.product.toString() + product.getId();
                Map<String, Object> value = data.get(key);
                if (value == null) {
                    continue;
                }
                value.put("product", map);
                data.put(key, value);
            }
        }
        if (CollectionUtils.isNotEmpty(producers)) {
            for (Producer producer : producers) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", producer.getId());
                map.put("name", producer.getName());
                map.put("image", producer.getImage());
                String key = BrowsingHistory.Type.producer.toString() + producer.getId();
                Map<String, Object> value = data.get(key);
                if (value == null) {
                    continue;
                }
                value.put("producer", map);
                data.put(key, value);
            }
        }
        return data.values().stream().filter(v -> v.get("product") != null || v.get("producer") != null).collect(Collectors.toList());
    }

    @Override
    public void deleteByUser(User user) {
        if(user == null){
            return;
        }
        browsingHistoryDao.deleteByUser(user);
    }
}