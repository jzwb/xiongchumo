package com.xcm.config;

import com.xcm.shiro.AuthenticationFilter;
import com.xcm.shiro.AuthenticationRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 配置 - Shiro
 */
@Configuration
public class ShiroConfig {

    @Bean
    public AuthenticationRealm authenticationRealm() {
        return new AuthenticationRealm();
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authenticationRealm());
        return securityManager;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/admin/login/");
        shiroFilterFactoryBean.setSuccessUrl("/admin/main/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/admin/common/unauthorized/");
        Map<String, Filter> filters = new HashMap<>();
        filters.put("authc", new AuthenticationFilter());

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/admin/login/", "authc");
        filterChainDefinitionMap.put("/admin/login/status/", "anon");
        filterChainDefinitionMap.put("/admin/logout/", "logout");
        filterChainDefinitionMap.put("/admin/admin/**", "perms[admin:admin]");
        filterChainDefinitionMap.put("/admin/role/**", "perms[admin:role]");
        filterChainDefinitionMap.put("/admin/menu/**", "perms[admin:menu]");
        filterChainDefinitionMap.put("/admin/storage_plugin/**", "perms[admin:storage_plugin]");
        filterChainDefinitionMap.put("/admin/**", "authc");

        shiroFilterFactoryBean.setFilters(filters);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
}