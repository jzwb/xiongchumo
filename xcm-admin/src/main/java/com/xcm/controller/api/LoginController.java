package com.xcm.controller.api;

import com.xcm.common.Message;
import com.xcm.exception.ServiceException;
import com.xcm.model.User;
import com.xcm.service.RSAService;
import com.xcm.service.UserService;
import com.xcm.util.ValidUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * controller - 登录
*/
@Controller("apiLoginController")
@RequestMapping("/api/login")
public class LoginController {

    static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RSAService rsaService;

    /**
     * 登录（手机、邮箱）
     *
     * @return
     */
    @PostMapping("/submit")
    @ResponseBody
    public Message submit(String username, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            String password = rsaService.decryptParameter("enPassword", request);
            rsaService.removePrivateKey(request);
            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                return Message.error("账号密码不允许为空");
            }
            User user = null;
            if (ValidUtils.isEmail(username)) {
                //邮箱登陆
                user = userService.findByEmail(username);
            } else if (ValidUtils.isMobile(username)) {
                //手机登陆
                user = userService.findByMobile(username);
            }
            if (user == null) {
                return Message.error("账号或密码错误");
            }
            userService.login(user, true, password, request, response, session);
            return Message.success("登陆成功");
        } catch (Exception e) {
            if (e instanceof ServiceException) {
                LOGGER.error("业务异常", e);
            } else {
                LOGGER.error("未知异常,username:{}", username, e);
            }
            return Message.error("登陆失败");
        }
    }
}