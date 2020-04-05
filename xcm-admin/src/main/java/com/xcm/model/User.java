package com.xcm.model;

import com.xcm.interceptor.UserInterceptor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *  Entity - 用户
 */
@Entity
@Table(name = "t_user")
public class User extends BaseEntity {

    public static final String PRINCIPAL_ATTRIBUTE_NAME = UserInterceptor.class.getName() + ".PRINCIPAL";

    public static final String USER_COOKIE_ID = "userId";

    private String mobile;//电话
    private String email;//邮箱
    private String password;//密码
    private String unionId;//微信unionId
    private String openId;//微信openId

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
