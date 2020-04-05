package com.xcm.service.impl;

import com.xcm.service.RSAService;
import com.xcm.util.RSAUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Service - RSA
 */
@Service
public class RSAServiceImpl implements RSAService {

    //私钥名称
    private static final String PRIVATE_KEY_NAME = "privateKey";

    @Override
    @Transactional(readOnly = true)
    public RSAPublicKey generateKey(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        KeyPair keyPair = RSAUtils.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        HttpSession session = request.getSession();
        session.setAttribute(PRIVATE_KEY_NAME, privateKey);
        return publicKey;
    }

    @Override
    @Transactional(readOnly = true)
    public void removePrivateKey(HttpServletRequest request) {
        if (request == null) {
            return;
        }
        HttpSession session = request.getSession();
        session.removeAttribute(PRIVATE_KEY_NAME);
    }

    @Override
    @Transactional(readOnly = true)
    public String decryptParameter(String name, HttpServletRequest request) {
        if (StringUtils.isBlank(name) || request == null) {
            return null;
        }
        HttpSession session = request.getSession();
        RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute(PRIVATE_KEY_NAME);
        String parameter = request.getParameter(name);
        if (privateKey == null || StringUtils.isBlank(parameter)) {
            return null;
        }
        return RSAUtils.decrypt(privateKey, parameter);
    }
}