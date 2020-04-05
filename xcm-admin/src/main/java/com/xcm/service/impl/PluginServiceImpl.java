package com.xcm.service.impl;

import com.xcm.plugin.StoragePlugin;
import com.xcm.service.PluginService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Service - 插件
 */
@Service
public class PluginServiceImpl implements PluginService {

    @Autowired
    private List<StoragePlugin> storagePlugins = new ArrayList<>();

    @Override
    public List<StoragePlugin> getStoragePlugins() {
        Collections.sort(storagePlugins);
        return storagePlugins;
    }

    @Override
    public List<StoragePlugin> getStoragePlugins(boolean isEnabled) {
        List<StoragePlugin> newStoragePlugins = new ArrayList<>();
        CollectionUtils.select(storagePlugins, o -> {
            StoragePlugin storagePlugin = (StoragePlugin) o;
            return storagePlugin.getIsEnabled() == isEnabled;
        }, newStoragePlugins);
        Collections.sort(newStoragePlugins);
        return newStoragePlugins;
    }
}