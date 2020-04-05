package com.xcm.plugin.file;

import com.xcm.common.Message;
import com.xcm.controller.admin.BaseController;
import com.xcm.model.PluginConfig;
import com.xcm.service.PluginConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller - 存储插件
 */
@Controller("adminStoragePluginFileController")
@RequestMapping("/admin/storage_plugin/file")
public class FileController extends BaseController {

    @Autowired
    private FilePlugin filePlugin;
    @Autowired
    private PluginConfigService pluginConfigService;

    /**
     * 安装
     */
    @RequestMapping(value = "/install", method = RequestMethod.POST)
    @ResponseBody
    public Message install() {
        if (!filePlugin.getIsInstalled()) {
            PluginConfig pluginConfig = new PluginConfig();
            pluginConfig.setPluginId(filePlugin.getId());
            pluginConfig.setIsEnabled(false);
            pluginConfigService.save(pluginConfig);
        }
        return Message.success();
    }
    /**
     * 卸载
     */
    @RequestMapping(value = "/uninstall", method = RequestMethod.POST)
    @ResponseBody
    public Message uninstall() {
        if (filePlugin.getIsInstalled()) {
            PluginConfig pluginConfig = filePlugin.getPluginConfig();
            pluginConfigService.delete(pluginConfig);
        }
        return Message.success();
    }

    /**
     * 设置
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String setting(ModelMap modelMap){
        PluginConfig pluginConfig = filePlugin.getPluginConfig();
        modelMap.addAttribute("pluginConfig", pluginConfig);
        return "/admin/storage_plugin/file_setting";
    }

    /**
     * 更新
     * @param order
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Message update(Integer order) {
        PluginConfig pluginConfig = filePlugin.getPluginConfig();
        pluginConfig.setIsEnabled(true);
        pluginConfig.setOrder(order);
        pluginConfigService.update(pluginConfig);
        return Message.success();
    }
}