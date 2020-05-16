package com.xcm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *  Entity - 商品收藏
 */
@Entity
@Table(name = "t_product_collection")
public class ProductCollection extends BaseEntity {

    private Long user;//用户
    private Long product;//商品

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
    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }
}