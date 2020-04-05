package com.xcm.dao.impl;

import com.xcm.dao.PluginConfigDao;
import com.xcm.model.PluginConfig;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

/**
 * Dao - 插件配置
 */
@Repository
public class PluginConfigDaoImpl extends BaseDaoImpl<PluginConfig, Long> implements PluginConfigDao {

	public boolean pluginIdExists(String pluginId) {
		if (pluginId == null) {
			return false;
		}
		String jpql = "SELECT COUNT(*) FROM PluginConfig pluginConfig WHERE pluginConfig.pluginId = :pluginId";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("pluginId", pluginId).getSingleResult();
		return count > 0;
	}

	public PluginConfig findByPluginId(String pluginId) {
		if (pluginId == null) {
			return null;
		}
		try {
			String jpql = "SELECT pluginConfig FROM PluginConfig pluginConfig WHERE pluginConfig.pluginId = :pluginId";
			return entityManager.createQuery(jpql, PluginConfig.class).setFlushMode(FlushModeType.COMMIT).setParameter("pluginId", pluginId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}