package com.xcm.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 *
 */
@Data
@PropertySource("classpath:setting.properties")
@ConfigurationProperties(prefix = "wx.miniapp")
public class WxMaProperties {

    private List<Config> configs;

    @Data
    public static class Config {
        private String appid;//设置微信小程序的appid
        private String secret;//设置微信小程序的Secret
        private String token;//设置微信小程序消息服务器配置的token
        private String aesKey;//设置微信小程序消息服务器配置的EncodingAESKey
        private String msgDataFormat;//消息格式，XML或者JSON
    }
}