package com.xcm.service;

import com.xcm.model.PluginConfig;

/**
 * Service - 插件配置
 */
public interface PluginConfigService extends BaseService<PluginConfig, Long> {

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