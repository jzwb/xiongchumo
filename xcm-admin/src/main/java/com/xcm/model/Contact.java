package com.xcm.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *  Entity - 联系我
 */
@Entity
@Table(name = "t_contact")
public class Contact extends BaseEntity {

    private String image;//图片
    private String description;//描述

    @Length(max = 200)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Length(max = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}