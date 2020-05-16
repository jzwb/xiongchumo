package com.xcm.controller.api.user;

import com.xcm.common.Filter;
import com.xcm.common.Message;
import com.xcm.model.ProductCollection;
import com.xcm.model.User;
import com.xcm.service.ProductCollectionService;
import com.xcm.service.ProductService;
import com.xcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * controller - 商品收藏
 */
@Controller("apiUserProductCollectionController")
@RequestMapping("/api/user/product_collection")
public class ProductCollectionController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCollectionService productCollectionService;

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Message list() {
        User user = userService.getCurrent();
        List<Map<String, Object>> list = productCollectionService.findList(user);
        return Message.success("请求成功", list);
    }

    /**
     * 添加
     *
     * @param productId 商品id
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Message add(Long productId) {
        if (productId == null) {
            return Message.error("参数错误");
        }
        if (!productService.exists(Filter.eq("id", productId))) {
            return Message.error("商品不存在");
        }
        User user = userService.getCurrent();
        if (productCollectionService.exists(Filter.eq("user", user.getId()), Filter.eq("product", productId))) {
            return Message.success();
        }
        ProductCollection productCollection = new ProductCollection();
        productCollection.setUser(user.getId());
        productCollection.setProduct(productId);
        productCollectionService.save(productCollection);
        return Message.success();
    }

    /**
     * 删除
     *
     * @param id 收藏id
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public Message del(Long id) {
        if (id == null) {
            return Message.error("参数错误");
        }
        User user = userService.getCurrent();
        ProductCollection productCollection = productCollectionService.find(id);
        if (productCollection == null) {
            return Message.success();
        }
        if (!user.equals(productCollection.getUser())) {
            return Message.error("无权取消收藏");
        }
        productCollectionService.delete(productCollection);
        return Message.success("请求成功");
    }
}