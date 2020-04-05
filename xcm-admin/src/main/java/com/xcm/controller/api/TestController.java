package com.xcm.controller.api;

import com.xcm.common.Message;
import com.xcm.service.RSAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * controller - 测试
 */
@Controller("apiTestController")
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private RSAService rsaService;

    /**
     * 测试get请求
     *
     * @return
     */
    @GetMapping("/get")
    @ResponseBody
    public Message get() {
        return Message.success();
    }

    /**
     * 测试post请求
     *
     * @return
     */
    @PostMapping("/post")
    @ResponseBody
    public Message post() {
        return Message.success();
    }

    /**
     * 测试rsa解密
     * @param request
     * @return
     */
    @GetMapping("/rsa_decrypt")
    @ResponseBody
    public Message rsaDecrypt(HttpServletRequest request) {
        String test = rsaService.decryptParameter("test", request);
        return Message.success(test);
    }
}