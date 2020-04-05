package com.xcm.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 微信小程序配置
 * http://binary.ac.cn/weixin-java-miniapp-javadoc/
 */
@Configuration
@EnableConfigurationProperties(WxMaProperties.class)
public class WxMaConfig {

    private WxMaProperties properties;

    @Autowired
    public WxMaConfig(WxMaProperties properties) {
        this.properties = properties;
    }

    private static Map<String, WxMaService> maServices = Maps.newHashMap();

    public static WxMaService getMaService(String appid) {
        WxMaService wxService = maServices.get(appid);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        return wxService;
    }

    @PostConstruct
    public void init() {
        List<WxMaProperties.Config> configs = this.properties.getConfigs();
        if (configs == null) {
            throw new RuntimeException("微信小程序配置异常");
        }
        maServices = configs.stream().map(c -> {
            WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
            config.setAppid(c.getAppid());
            config.setSecret(c.getSecret());
            /*config.setToken(c.getToken());
            config.setAesKey(c.getAesKey());
            config.setMsgDataFormat(c.getMsgDataFormat());*/

            WxMaService service = new WxMaServiceImpl();
            service.setWxMaConfig(config);
            return service;
        }).collect(Collectors.toMap(c -> c.getWxMaConfig().getAppid(), c -> c));
    }
}