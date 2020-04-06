package com.xcm.model;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 *  Entity - 商品分类
 */
@Entity
@Table(name = "t_product_category")
public class ProductCategory extends OrderEntity {

    public static final String TREE_PATH_SEPARATOR = ",";//树路径分隔符

    private String name;//名称
    private String fullName;//全名称
    private String treePath;//树路径
    private Integer grade;//层级
    private ProductCategory parent;//父级
    private List<ProductCategory> children = new ArrayList<>();//子级

    @NotEmpty
    @Length(max = 200)
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public ProductCategory getParent() {
        return parent;
    }

    public void setParent(ProductCategory parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OrderBy("order asc, id asc")
    public List<ProductCategory> getChildren() {
        return children;
    }

    public void setChildren(List<ProductCategory> children) {
        this.children = children;
    }

    /**
     * 获取树路径
     * @return
     */
    @Transient
    public List<Long> getTreePaths() {
        List<Long> treePaths = new ArrayList<>();
        String[] ids = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
        if (ids != null) {
            for (String id : ids) {
                treePaths.add(Long.valueOf(id));
            }
        }
        return treePaths;
    }
}