package com.xcm.dao;

import com.xcm.model.PluginConfig;

/**
 * Dao - 插件配置
 */
public interface PluginConfigDao extends BaseDao<PluginConfig, Long> {

	/**
	 * 判断插件ID是否存在
	 *
	 * @param pluginId 插件ID
	 * @return
	 */
	boolean pluginIdExists(String pluginId);

	/**
	 * 根据插件ID查找插件配置
	 *
	 * @param pluginId 插件ID
	 * @return
	 */
	PluginConfig findByPluginId(String pluginId);
}