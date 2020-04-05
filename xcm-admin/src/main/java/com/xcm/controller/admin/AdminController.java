package com.xcm.controller.admin;

import com.xcm.common.Message;
import com.xcm.common.Page;
import com.xcm.common.Pageable;
import com.xcm.model.Admin;
import com.xcm.service.AdminService;
import com.xcm.service.RoleService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * controller - 用户管理
 */
@Controller("adminAdminController")
@RequestMapping("/admin/admin")
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "/admin/admin/index";
    }

    /**
     * 列表
     * @param pageable
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Message list(Pageable pageable) {
        Page<Admin> page = adminService.findPage(pageable);
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
        modelMap.addAttribute("admin", adminService.find(id));
        modelMap.addAttribute("roles", roleService.findAll());
        return "/admin/admin/form";
    }

    /**
     * 保存
     *
     * @param pAdmin
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Message save(Admin pAdmin, Long[] roleIds) {
        if(pAdmin == null){
            return Message.error("参数错误");
        }
        Admin admin;
        if (pAdmin.getId() == null) {
            //新增
            if (adminService.usernameExists(pAdmin.getUsername())) {
                return Message.error("用户名已存在");
            }
            admin = new Admin();
            admin.setUsername(pAdmin.getUsername());
            admin.setPassword(DigestUtils.md5Hex(pAdmin.getPassword()));
        } else {
            //编辑
            admin = adminService.find(pAdmin.getId());
            if (StringUtils.isNotBlank(pAdmin.getPassword())) {
                admin.setPassword(DigestUtils.md5Hex(pAdmin.getPassword()));
            }
        }
        admin.setRoles(new HashSet<>(roleService.findList(roleIds)));
        admin.setEmail(pAdmin.getEmail());
        admin.setMobile(pAdmin.getMobile());
        admin.setName(pAdmin.getName());
        if (admin.getId() == null) {
            adminService.save(admin);
        } else {
            adminService.update(admin);
        }
        return Message.success();
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public Message delete(Long[] ids) {
        if(ids.length >= adminService.count()){
            return  Message.error("删除失败");
        }
        adminService.delete(ids);
        return Message.success();
    }
}