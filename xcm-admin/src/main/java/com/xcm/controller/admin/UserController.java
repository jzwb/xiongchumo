package com.xcm.controller.admin;

import com.xcm.common.Message;
import com.xcm.common.Page;
import com.xcm.common.Pageable;
import com.xcm.exception.ServiceException;
import com.xcm.model.User;
import com.xcm.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * controller - 用户管理
 */
@Controller("adminUserController")
@RequestMapping("/admin/user")
public class UserController extends BaseController {


    static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "/admin/user/index";
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
        Page<User> page = userService.findPage(pageable);
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
        modelMap.addAttribute("user", userService.find(id));
        return "/admin/user/form";
    }

    /**
     * 保存
     *
     * @param pUser
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Message save(User pUser) {
        try {
            if (pUser == null) {
                return Message.error("参数错误");
            }
            String email = pUser.getEmail();
            String mobile = pUser.getMobile();
            User user;
            if (pUser.getId() == null) {
                //新增
                if (StringUtils.isNotBlank(email) && userService.emailExists(email)) {
                    return Message.error("邮箱已存在");
                }
                if (StringUtils.isNotBlank(mobile) && userService.mobileExists(mobile)) {
                    return Message.error("手机已存在");
                }
                user = new User();
                user.setPassword(DigestUtils.md5Hex(pUser.getPassword()));
            } else {
                //编辑
                user = userService.find(pUser.getId());
                if (StringUtils.isNotBlank(email) && !userService.emailUnique(user.getEmail(), email)) {
                    return Message.error("邮箱已存在");
                }
                if (StringUtils.isNotBlank(mobile) && !userService.mobileUnique(user.getMobile(), mobile)) {
                    return Message.error("手机已存在");
                }
                if (StringUtils.isNotBlank(pUser.getPassword())) {
                    user.setPassword(DigestUtils.md5Hex(pUser.getPassword()));
                }
            }
            user.setEmail(email);
            user.setMobile(mobile);
            user.setNickName(pUser.getNickName());
            user.setHead(pUser.getHead());
            if (user.getId() == null) {
                userService.save(user);
            } else {
                userService.update(user);
            }
            return Message.success("保存成功");
        } catch (Exception e) {
            if (e instanceof ServiceException) {
                LOGGER.error("业务异常", e);
            } else {
                LOGGER.error("未知异常", e);
            }
            return Message.error("保存失败");
        }
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
        userService.delete(ids);
        return Message.success();
    }

    /**
     * 检查邮箱唯一
     * @param oldEmail 旧邮箱
     * @param newEmail 新邮箱
     * @return
     */
    @GetMapping("/check_email_unique")
    @ResponseBody
    public Message checkEmailUnique(String oldEmail, String newEmail) {
        return Message.success("请求成功", userService.emailUnique(oldEmail, newEmail));
    }

    /**
     * 检查手机唯一
     * @param oldMobile 旧邮箱
     * @param newMobile 新邮箱
     * @return
     */
    @GetMapping("/check_mobile_unique")
    @ResponseBody
    public Message checkMobileUnique(String oldMobile, String newMobile) {
        return Message.success("请求成功", userService.mobileUnique(oldMobile, newMobile));
    }
}