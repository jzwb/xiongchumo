package com.xcm.service;

import javax.servlet.http.HttpServletRequest;
import java.security.interfaces.RSAPublicKey;

/**
 * Service - RSA
 */
public interface RSAService {

    /**
     * 生成密钥(私钥存储Session并返回公钥)
     *
     * @param request
     * @return
     */
    RSAPublicKey generateKey(HttpServletRequest request);

    /**
     * 移除私钥
     *
     * @param request httpServletRequest
     */
    void removePrivateKey(HttpServletRequest request);

    /**
     * 解密参数
     *
     * @param name    参数名称
     * @param request
     * @return
     */
    String decryptParameter(String name, HttpServletRequest request);
}