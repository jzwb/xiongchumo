package com.xcm.controller.admin;

import com.xcm.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * controller - 后台登录
 */
@Controller("adminLoginController")
@RequestMapping("/admin")
public class LoginController extends BaseController {

    @Autowired
    private AdminService adminService;

    /**
     * 登录
     *
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login() {
        if (adminService.isLogin()) {
            return "redirect:/admin/main/";
        }
        return "/admin/login";
    }
}
