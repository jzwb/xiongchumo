package com.xcm.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Tools - 工具类
 */
public class Tools {

    /**
     * 移除域名
     *
     * @param url
     * @return
     */
    public static String removeDomain(String url) {
        if (StringUtils.isEmpty(url)) {
            return "";
        }
        String pattern = "^((http://)|(https://))?([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}(/)";
        return url.replaceAll(pattern, "/");
    }

    /**
     * 获取IP地址
     *
     * @param request 请求
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip) && ip.split(",").length > 0) {
            ip = ip.split(",")[0].trim();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
