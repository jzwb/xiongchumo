package com.xcm.controller.api;

import com.xcm.common.Message;
import com.xcm.model.Producer;
import com.xcm.model.Product;
import com.xcm.service.ProducerService;
import com.xcm.service.ProductService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * controller - 商品
 */
@Controller("apiProductController")
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProducerService producerService;
    @Autowired
    private ProductService productService;

    /**
     * 列表
     *
     * @param pageNumber 页码
     * @param pageSize   页数量
     * @param type       类型
     * @param sortType   排序类型
     * @return
     */
    @GetMapping(value = "/list")
    @ResponseBody
    public Message list(@RequestParam(defaultValue = "1", required = false) Integer pageNumber, @RequestParam(defaultValue = "50", required = false) Integer pageSize, Producer.Type type, @RequestParam(defaultValue = "NEW") Product.SortType sortType) {
        List<Product> products = productService.findList(pageNumber, pageSize, type, sortType);
        List<Map<String, Object>> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(products)) {
            for (Product product : products) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", product.getId());
                map.put("title", product.getTitle());
                map.put("images", product.getImages());
                map.put("views", product.getViews());
                map.put("likes", product.getLikes());
                map.put("createDate", product.getCreateDate().getTime());
                list.add(map);
            }
        }
        return Message.success("请求成功", list);
    }

    /**
     * 详情
     *
     * @param id 商品id
     * @return
     */
    @GetMapping(value = "/detail")
    @ResponseBody
    public Message detail(Long id) {
        Product product = productService.find(id);
        if (product == null) {
            return Message.error("商品不存在");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", product.getId());
        map.put("title", product.getTitle());
        map.put("subTitle", product.getSubTitle());
        map.put("images", product.getImages());
        map.put("views", product.getViews());
        map.put("likes", product.getLikes());
        map.put("content", product.getContent());
        map.put("createDate", product.getCreateDate().getTime());
        Producer producer = product.getProducer();
        Map<String, Object> producerMap = new HashMap<>();
        producerMap.put("id", producer.getId());
        producerMap.put("name", producer.getName());
        producerMap.put("image", producer.getImage());
        map.put("producer", producerMap);
        return Message.success("请求成功", map);
    }

    /**
     * 点赞
     *
     * @param id 商品id
     * @return
     */
    @GetMapping(value = "/likes")
    @ResponseBody
    public Message likes(Long id) {
        Product product = productService.find(id);
        if (product == null) {
            return Message.error("商品不存在");
        }
        product.setLikes(product.getLikes() + 1);
        productService.update(product);
        return Message.success();
    }

    /**
     * 浏览
     *
     * @param id 商品id
     * @return
     */
    @GetMapping(value = "/views")
    @ResponseBody
    public Message views(Long id) {
        Product product = productService.find(id);
        if (product == null) {
            return Message.error("商品不存在");
        }
        product.setViews(product.getViews() + 1);
        productService.update(product);
        return Message.success();
    }

    /**
     * 列表（根据生产商分组）
     *
     * @param pageNumber 页码
     * @param type       类型
     * @param sortType   排序类型
     * @return
     */
    @GetMapping(value = "/list/group_by_producer")
    @ResponseBody
    public Message list_group_by_producer(@RequestParam(defaultValue = "1", required = false) Integer pageNumber, Producer.Type type, @RequestParam(defaultValue = "NEW") Producer.SortType sortType) {
        List<Producer> producers = producerService.findList(pageNumber, 10, type, sortType);
        List<Map<String, Object>> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(producers)) {
            for (Producer producer : producers) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", producer.getId());
                map.put("name", producer.getName());
                map.put("image", producer.getImage());
                map.put("views", producer.getViews());
                map.put("likes", producer.getLikes());
                map.put("createDate", producer.getCreateDate().getTime());
                //商品
                List<Product> products = productService.findList(pageNumber, 4, producer, Product.SortType.NEW);
                List<Map<String, Object>> newProducts = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(products)) {
                    for (Product product : products) {
                        Map<String, Object> newProduct = new HashMap<>();
                        newProduct.put("id", product.getId());
                        newProduct.put("title", product.getTitle());
                        newProduct.put("images", product.getImages());
                        newProduct.put("views", product.getViews());
                        newProduct.put("likes", product.getLikes());
                        newProduct.put("createDate", product.getCreateDate().getTime());
                        newProducts.add(newProduct);
                    }
                }
                map.put("products", newProducts);
                list.add(map);
            }
        }
        return Message.success("请求成功", list);
    }

    /**
     * 列表（根据生产商）
     *
     * @param pageNumber 页码
     * @param pageSize   页数量
     * @param producerId 生产商id
     * @param sortType   排序类型
     * @return
     */
    @GetMapping(value = "/list/by_producer")
    @ResponseBody
    public Message list_by_producer(@RequestParam(defaultValue = "1", required = false) Integer pageNumber, @RequestParam(defaultValue = "50", required = false) Integer pageSize, Long producerId, @RequestParam(defaultValue = "NEW") Product.SortType sortType) {
        Producer producer = producerService.find(producerId);
        if (producer == null) {
            return Message.error("生产商不存在");
        }
        List<Product> products = productService.findList(pageNumber, pageSize, producer, sortType);
        List<Map<String, Object>> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(products)) {
            for (Product product : products) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", product.getId());
                map.put("title", product.getTitle());
                map.put("images", product.getImages());
                map.put("views", product.getViews());
                map.put("likes", product.getLikes());
                map.put("createDate", product.getCreateDate().getTime());
                list.add(map);
            }
        }
        return Message.success("请求成功", list);
    }
}