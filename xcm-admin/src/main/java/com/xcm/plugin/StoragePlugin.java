package com.xcm.plugin;

import com.xcm.common.FileInfo;
import com.xcm.model.PluginConfig;
import com.xcm.service.PluginConfigService;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * 插件 - 存储
 */
public abstract class StoragePlugin implements  Comparable<StoragePlugin> {

    @Autowired
    private PluginConfigService pluginConfigService;

    /**
     * 获取ID
     *
     * @return
     */
    public final String getId() {
        return getClass().getAnnotation(Component.class).value();
    }

    /**
     * 获取名称
     *
     * @return
     */
    public abstract String getName();

    /**
     * 获取版本
     *
     * @return
     */
    public abstract String getVersion();

    /**
     * 获取网址
     *
     * @return
     */
    public abstract String getSiteUrl();

    /**
     * 获取安装URL
     *
     * @return
     */
    public abstract String getInstallUrl();

    /**
     * 获取卸载URL
     *
     * @return
     */
    public abstract String getUninstallUrl();

    /**
     * 获取设置URL
     *
     * @return
     */
    public abstract String getSettingUrl();

    /**
     * 获取是否已安装
     *
     * @return
     */
    public boolean getIsInstalled() {
        return pluginConfigService.pluginIdExists(getId());
    }

    /**
     * 获取插件配置
     *
     * @return
     */
    public PluginConfig getPluginConfig() {
        return pluginConfigService.findByPluginId(getId());
    }

    /**
     * 获取是否已启用
     *
     * @return
     */
    public boolean getIsEnabled() {
        PluginConfig pluginConfig = getPluginConfig();
        return pluginConfig != null ? pluginConfig.getIsEnabled() : false;
    }

    /**
     * 获取属性值
     *
     * @param name 属性名称
     * @return
     */
    public String getAttribute(String name) {
        PluginConfig pluginConfig = getPluginConfig();
        return pluginConfig != null ? pluginConfig.getAttribute(name) : null;
    }

    /**
     * 获取排序
     *
     * @return
     */
    public Integer getOrder() {
        PluginConfig pluginConfig = getPluginConfig();
        return pluginConfig != null ? pluginConfig.getOrder() : null;
    }

    /**
     * 文件上传
     *
     * @param path        上传路径
     * @param file        上传文件
     * @param contentType 文件类型
     */
    public abstract void upload(String path, File file, String contentType);


    /**
     * 获取File
     *
     * @param path
     */
    public abstract File getFile(String path);

    /**
     * 删除file
     *
     * @param path
     */
    public abstract void deleteFile(String path);


    /**
     * 替换文件
     *
     * @param newPath
     * @param oldPath
     * @return
     */
    public abstract String resplace(String newPath, String oldPath);


    /**
     * 压缩
     *
     * @return
     */
    public abstract String zoom(String srcFile, int destWidth, int destHeight);

    /**
     * 计算文件大小
     *
     * @param path
     * @return
     */
    public abstract double calculateFileSize(String path);

    /**
     * 获取访问URL
     *
     * @param path 上传路径
     * @return
     */
    public abstract String getUrl(String path);

    /**
     * 文件浏览
     *
     * @param path 浏览路径
     * @return
     */
    public abstract List<FileInfo> browser(String path);

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        StoragePlugin other = (StoragePlugin) obj;
        return new EqualsBuilder().append(getId(), other.getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
    }

    public int compareTo(StoragePlugin storagePlugin) {
        return new CompareToBuilder().append(getOrder(), storagePlugin.getOrder()).append(getId(), storagePlugin.getId()).toComparison();
    }
}
