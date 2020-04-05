package com.xcm.controller.api;

import com.xcm.common.Message;
import com.xcm.service.RSAService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * controller - 通用
 */
@Controller("apiCommonController")
@RequestMapping("/api/common")
public class CommonController {

    @Autowired
    private RSAService rsaService;

    /**
     * 公钥
     */
    @GetMapping(value = "/get_public_key")
    @ResponseBody
    public Message getPublicKey(HttpServletRequest request) {
        RSAPublicKey publicKey = rsaService.generateKey(request);
        Map<String, String> data = new HashMap<>();
        data.put("modulus", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
        data.put("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));
        return Message.success("请求成功", data);
    }
}
