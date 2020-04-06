package com.xcm.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *  Entity - 商品
 */
@Entity
@Table(name = "t_product")
public class Product extends OrderEntity {

    private String title;//标题
    private String subTitle;//子标题
    private String firstImages;//首图
    private Long views = 0L;//浏览量
    private Long likes = 0L;//点赞数
    private String content;//内容
    private ProductCategory productCategory;//商品分类
    private Producter producter;//生产商

    @NotEmpty
    @Length(max = 200)
    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Length(max = 200)
    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getFirstImages() {
        return firstImages;
    }

    public void setFirstImages(String firstImages) {
        this.firstImages = firstImages;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    public Producter getProducter() {
        return producter;
    }

    public void setProducter(Producter producter) {
        this.producter = producter;
    }
}