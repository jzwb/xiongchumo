package com.xcm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 *  Entity - 菜单
 */
@Entity
@Table(name = "sys_menu")
public class Menu extends OrderEntity {

    public static final String TREE_PATH_SEPARATOR = ",";//树路径分隔符

    private String title;//标题
    private String href;//链接
    private String icon;//图标
    private String target = "_self";//打开方式
    private Integer grade;//层级
    private String treePath;//树路径
    private String authority;//权限
    private Menu parent;//上级
    private List<Menu> children = new ArrayList<>();//下级

    @JsonProperty
    @NotEmpty
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @JsonProperty
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @JsonProperty
    @NotEmpty
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @JsonProperty
    @NotNull
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @JsonProperty
    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    @JsonProperty
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

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
