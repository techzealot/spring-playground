package com.techzealot.spring.playground.controller;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public FilterRegistrationBean requestFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ReusedRequestWrapperFilter());
        // 过滤应用程序中所有资源,当前应用程序根下的所有文件包括多级子目录下的所有文件，注意这里*前有“/”
        registration.addUrlPatterns("/requestReuse/*");
        registration.setName("RequestWrapperFilter");
        return registration;
    }

    /**
     * before request由于未消费不缓存body数据,因此无法获取
     * after request可以获取到
     *
     * @return
     */
    //@Bean
    public CommonsRequestLoggingFilter loggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeHeaders(true);
        filter.setIncludeClientInfo(true);
        filter.setIncludePayload(true);
        filter.setIncludeQueryString(true);
        return filter;
    }
}
