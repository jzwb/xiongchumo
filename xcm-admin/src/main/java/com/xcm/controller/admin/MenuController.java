package com.xcm.controller.admin;

import com.xcm.common.Message;
import com.xcm.model.Menu;
import com.xcm.service.MenuService;
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
 * controller - 菜单管理
 */
@Controller("adminMenuController")
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "admin/menu/index";
    }

    /**
     * 列表
     *
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Message list() {
        List<Menu> menus = menuService.findTree();
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Menu menu : menus) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", menu.getId());
            map.put("title", menu.getTitle());
            map.put("href", menu.getHref());
            map.put("icon", menu.getIcon());
            map.put("target", menu.getTarget());
            map.put("grade", menu.getGrade());
            map.put("treePath", menu.getTreePath());
            map.put("authority", menu.getAuthority());
            map.put("order", menu.getOrder());
            Menu parent = menu.getParent();
            map.put("parent", parent != null ? parent.getId() : -1);
             mapList.add(map);
        }
        return Message.success("成功", mapList);
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
        modelMap.put("menu", menuService.find(id));
        modelMap.put("menus", menuService.findTree());
        return "admin/menu/form";
    }

    /**
     * 保存
     * @param menu
     * @param parentId
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Message save(Menu menu, Long parentId) {
        Menu pMenu = menuService.find(menu.getId());
        if (pMenu == null) {
            pMenu = new Menu();
        }
        pMenu.setTitle(menu.getTitle());
        pMenu.setParent(menuService.find(parentId));
        pMenu.setHref(menu.getHref());
        pMenu.setTarget(menu.getTarget());
        pMenu.setIcon(menu.getIcon());
        pMenu.setOrder(menu.getOrder());
        pMenu.setAuthority(menu.getAuthority());
        if (pMenu.getId() == null) {
            menuService.save(pMenu);
        } else {
            menuService.update(pMenu);
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
        menuService.delete(ids);
        return Message.success();
    }
}