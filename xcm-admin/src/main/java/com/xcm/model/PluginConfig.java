package com.xcm.model;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity - 插件配置
 */
@Entity
@Table(name = "sys_plugin_config")
public class PluginConfig extends OrderEntity {

	private String pluginId;//插件ID
	private Boolean isEnabled;//是否启用
	private Map<String, String> attributes = new HashMap<>();//属性

	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getPluginId() {
		return pluginId;
	}

	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

	@Column(nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "sys_plugin_config_attribute")
	@MapKeyColumn(name = "name", length = 100)
	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	/**
	 * 获取属性值
	 *
	 * @param name 属性名称
	 * @return 属性值
	 */
	@Transient
	public String getAttribute(String name) {
		if (getAttributes() != null && name != null) {
			return getAttributes().get(name);
		} else {
			return null;
		}
	}

	/**
	 * 设置属性值
	 *
	 * @param name  属性名称
	 * @param value 属性值
	 */
	@Transient
	public void setAttribute(String name, String value) {
		if (getAttributes() != null && name != null) {
			getAttributes().put(name, value);
		}
	}
}