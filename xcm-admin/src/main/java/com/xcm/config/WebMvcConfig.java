package com.xcm.config;

import com.xcm.filter.CookieFilter;
import com.xcm.interceptor.CommonInterceptor;
import com.xcm.interceptor.LogInterceptor;
import com.xcm.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置 - 拦截器、视图
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private CommonInterceptor commonInterceptor;
    @Autowired
    private LogInterceptor logInterceptor;
    @Autowired
    private UserInterceptor userInterceptor;

    /**
     * 拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor).addPathPatterns("/admin/**");
        registry.addInterceptor(logInterceptor).addPathPatterns("/admin/**");
        registry.addInterceptor(userInterceptor).addPathPatterns("/api/user/**");
    }

    /**
     * 视图
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //后台
        registry.addViewController("/admin/index/").setViewName("/admin/index");//首页
        registry.addViewController("/admin/main/").setViewName("/admin/main");//页面框架
    }

    /**
     * 过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean cookieFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new CookieFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

    /**
     * 过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean shiroFilterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new DelegatingFilterProxy("shiroFilter"));
        registrationBean.addUrlPatterns("/admin/*");
        registrationBean.setName("shiroFilter");
        return registrationBean;
    }
}