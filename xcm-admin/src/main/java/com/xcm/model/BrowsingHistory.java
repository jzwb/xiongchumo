package com.xcm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *  Entity - 浏览历史
 */
@Entity
@Table(name = "t_browsing_history")
public class BrowsingHistory extends BaseEntity {

    /**
     * 类型枚举
     */
    public enum Type {
        product("商品"),
        producer("生产商");

        private String desc;

        Type(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public static Type valueOf(int ordinal) {
            if (ordinal < 0 || ordinal >= values().length) {
                throw new IndexOutOfBoundsException("Invalid ordinal");
            }
            return values()[ordinal];
        }
    }

    private Type type;//类型
    private Long user;//用户
    private Long relId;//引用id

    @NotNull
    @Column(nullable = false, updatable = false)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @NotNull
    @Column(nullable = false, updatable = false)
    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    @NotNull
    @Column(nullable = false, updatable = false)
    public Long getRelId() {
        return relId;
    }

    public void setRelId(Long relId) {
        this.relId = relId;
    }
}