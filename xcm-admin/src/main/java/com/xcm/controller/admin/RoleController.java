package com.xcm.controller.admin;

import com.xcm.common.Message;
import com.xcm.common.Page;
import com.xcm.common.Pageable;
import com.xcm.model.Role;
import com.xcm.service.RoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * controller - 角色管理
 */
@Controller("adminRoleController")
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "admin/role/index";
    }

    /**
     * 列表
     *
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Message list(Pageable pageable) {
        Page<Role> page = roleService.findPage(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("content", page.getContent());
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
        modelMap.put("role", roleService.find(id));
        return "admin/role/form";
    }

    /**
     * 保存
     *
     * @param role
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Message save(Role role) {
        Role pRole = roleService.find(role.getId());
        if (pRole == null) {
            pRole = new Role();
            pRole.setIsSystem(false);
        }
        pRole.setName(role.getName());
        pRole.setDescription(role.getDescription());
        pRole.setAuthorities(role.getAuthorities());
        if (pRole.getId() == null) {
            roleService.save(pRole);
        } else {
            roleService.update(pRole);
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
        for (Long id : ids) {
            Role role = roleService.find(id);
            if (role != null && (role.getIsSystem() || CollectionUtils.isNotEmpty(role.getAdmins()))) {
                return Message.error("无法删除系统内置角色");
            }
        }
        roleService.delete(ids);
        return Message.success();
    }
}