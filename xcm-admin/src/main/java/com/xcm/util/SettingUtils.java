package com.xcm.util;

import com.xcm.common.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 配置类静态化工具
 */
@Component
public class SettingUtils {
    private SettingUtils() {
    }

    public static Setting setting;

    public static Setting get() {
        return setting;
    }

    @Autowired
    public void setSetting(Setting setting) {
        SettingUtils.setting = setting;
    }
}
