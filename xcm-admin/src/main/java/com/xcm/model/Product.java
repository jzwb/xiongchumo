package com.xcm.model;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

/**
 *  Entity - 商品
 */
@Entity
@Table(name = "t_product")
public class Product extends OrderEntity {

    private String title;//标题
    private String subTitle;//子标题
    private String imagesJson;//图片
    private Long views = 0L;//浏览量
    private Long likes = 0L;//点赞数
    private String content;//内容
    private ProductCategory productCategory;//商品分类
    private Producer producer;//生产商
    private Boolean isTop = Boolean.FALSE;//置顶

    @JsonProperty
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

    public String getImagesJson() {
        return imagesJson;
    }

    public void setImagesJson(String imagesJson) {
        this.imagesJson = imagesJson;
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
    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    /**
     * 获取图片
     *
     * @return
     */
    @Transient
    public List<String> getImages() {
        try {
            String ImagesJson = getImagesJson();
            if (StringUtils.isNotBlank(ImagesJson)) {
                return JSONArray.parseArray(ImagesJson, String.class);
            }
        } catch (Exception ignored) {
        }
        return Collections.emptyList();
    }
}