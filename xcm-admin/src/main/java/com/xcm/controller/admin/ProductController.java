package com.xcm.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.xcm.common.Message;
import com.xcm.common.Page;
import com.xcm.common.Pageable;
import com.xcm.model.Producer;
import com.xcm.model.Product;
import com.xcm.model.ProductCategory;
import com.xcm.service.ProducerService;
import com.xcm.service.ProductCategoryService;
import com.xcm.service.ProductService;
import com.xcm.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * controller - 商品管理
 */
@Controller("adminProductController")
@RequestMapping("/admin/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProducerService producerService;

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "admin/product/index";
    }

    /**
     * 列表
     *
     * @param pageable
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Message list(Pageable pageable) {
        Page<Product> page = productService.findPage(pageable);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Product product : page.getContent()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", product.getId());
            map.put("title", product.getTitle());
            ProductCategory productCategory = product.getProductCategory();
            map.put("productCategory", productCategory != null ? productCategory.getName() : null);
            Producer producer = product.getProducer();
            map.put("producer", producer != null ? producer.getName() : null);
            map.put("createDate", DateTimeUtils.getFormatDate(product.getCreateDate(), DateTimeUtils.FULL_DATE_FORMAT));
            mapList.add(map);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("content", mapList);
        return Message.success("成功", data);
    }

    /**
     * 表单
     *
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping("/form")
    public String form(Long id, ModelMap modelMap) {
        modelMap.addAttribute("product", productService.find(id));
        modelMap.addAttribute("productCategories", productCategoryService.findTree());
        modelMap.addAttribute("producers", producerService.findAll());
        return "/admin/product/form";
    }

    /**
     * 保存
     *
     * @param product           商品
     * @param productCategoryId 商品分类id
     * @param producerId        生厂商id
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Message save(Product product, Long productCategoryId, Long producerId, String[] images) {
        Product pProduct = productService.find(product.getId());
        if (pProduct == null) {
            pProduct = new Product();
        }
        pProduct.setTitle(product.getTitle());
        pProduct.setSubTitle(product.getSubTitle());
        pProduct.setImagesJson(JSONArray.toJSONString(images != null ? images : new String[]{}));
        pProduct.setViews(product.getViews());
        pProduct.setLikes(product.getLikes());
        pProduct.setContent(product.getContent());
        pProduct.setProductCategory(productCategoryService.find(productCategoryId));
        pProduct.setProducer(producerService.find(producerId));
        pProduct.setIsTop(product.getIsTop());
        pProduct.setOrder(product.getOrder());
        if (pProduct.getId() == null) {
            productService.save(pProduct);
        } else {
            productService.update(pProduct);
        }
        return Message.success();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public Message delete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return Message.error("参数错误");
        }
        productService.delete(ids);
        return Message.success();
    }
}