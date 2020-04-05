package com.xcm.config;

import com.xcm.directive.FlashMessageDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 配置 - Freemarker
 */
@Configuration
public class FreeMarkerConfig {

    @Autowired
    private freemarker.template.Configuration configuration;
    @Autowired
    private FlashMessageDirective flashMessageDirective;

    @PostConstruct
    public void setSharedVariable() {
        try {
            configuration.setSharedVariable("flashMessage", flashMessageDirective);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}