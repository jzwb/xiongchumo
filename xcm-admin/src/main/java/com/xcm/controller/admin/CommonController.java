package com.xcm.controller.admin;

import com.xcm.common.Message;
import com.xcm.model.Menu;
import com.xcm.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * controller - 通用
 */
@Controller("adminCommonController")
@RequestMapping("/admin/common")
public class CommonController {

    @Autowired
    private MenuService menuService;

    /**
     * 列表
     *
     * @return
     */
    @GetMapping("/menus")
    @ResponseBody
    public Message menus() {
        Subject subject = SecurityUtils.getSubject();
        List<Menu> menus = menuService.findTree();
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Menu menu : menus) {
            String authority = menu.getAuthority();
            if (StringUtils.isNotBlank(authority) && !subject.isPermitted(menu.getAuthority())) {
                continue;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id", menu.getId());
            map.put("title", menu.getTitle());
            map.put("href", menu.getHref());
            map.put("icon", menu.getIcon());
            map.put("target", menu.getTarget());
            map.put("grade", menu.getGrade());
            map.put("treePath", menu.getTreePath());
            Menu parent = menu.getParent();
            map.put("parent", parent != null ? parent.getId() : -1);
            map.put("order", menu.getOrder());
            mapList.add(map);
        }
        return Message.success("成功", mapList);
    }

    /**
     * 无权
     *
     * @return
     */
    @GetMapping("/unauthorized")
    @ResponseBody
    public Message unauthorized() {
        return Message.error("无权访问");
    }
}
