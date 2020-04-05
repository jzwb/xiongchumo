package com.xcm.util;

import org.apache.commons.lang.StringUtils;

/**
 * Tools - 相关校验
 */
public class ValidUtils {

    //邮箱正则
    public final static String EMAIL_REG = "^[a-zA-Z0-9_\\.-]+@.+\\.[a-zA-Z]+$";
    //手机正则
    public final static String MOBILE_REG = "^1\\d{10}$";

    /**
     * 校验是否邮箱
     *
     * @param email 邮箱
     * @return
     */
    public static boolean isEmail(String email) {
        return StringUtils.isNotBlank(email) && email.matches(EMAIL_REG);
    }

    /**
     * 校验是否手机
     *
     * @param mobile 手机
     * @return
     */
    public static boolean isMobile(String mobile) {
        return StringUtils.isNotBlank(mobile) && mobile.matches(MOBILE_REG);
    }
}