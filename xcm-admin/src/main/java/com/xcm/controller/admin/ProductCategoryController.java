package com.xcm.controller.admin;

import com.xcm.common.Message;
import com.xcm.model.ProductCategory;
import com.xcm.service.ProductCategoryService;
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
 * controller - 商品分类管理
 */
@Controller("adminProductCategoryController")
@RequestMapping("/admin/product_category")
public class ProductCategoryController extends BaseController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "admin/product_category/index";
    }

    /**
     * 列表
     *
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Message list() {
        List<ProductCategory> productCategories = productCategoryService.findTree();
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (ProductCategory productCategory : productCategories) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", productCategory.getId());
            map.put("name", productCategory.getName());
            map.put("grade", productCategory.getGrade());
            map.put("treePath", productCategory.getTreePath());
            map.put("order", productCategory.getOrder());
            ProductCategory parent = productCategory.getParent();
            map.put("parent", parent != null ? parent.getId() : -1);
            mapList.add(map);
        }
        return Message.success("请求成功", mapList);
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
        modelMap.put("productCategory", productCategoryService.find(id));
        modelMap.put("productCategories", productCategoryService.findTree());
        return "admin/product_category/form";
    }

    /**
     * 保存
     *
     * @param productCategory 商品分类
     * @param parentId 父级id
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Message save(ProductCategory productCategory, Long parentId) {
        ProductCategory pProductCategory = productCategoryService.find(productCategory.getId());
        if (pProductCategory == null) {
            pProductCategory = new ProductCategory();
        }
        pProductCategory.setName(productCategory.getName());
        pProductCategory.setParent(productCategoryService.find(parentId));
        pProductCategory.setOrder(productCategory.getOrder());
        if (pProductCategory.getParent() != null) {
            pProductCategory.setFullName(pProductCategory.getParent().getFullName() + "," + pProductCategory.getName());
        } else {
            pProductCategory.setFullName(pProductCategory.getName());
        }
        if (pProductCategory.getId() == null) {
            productCategoryService.save(pProductCategory);
        } else {
            productCategoryService.update(pProductCategory);
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
        productCategoryService.delete(ids);
        return Message.success();
    }
}