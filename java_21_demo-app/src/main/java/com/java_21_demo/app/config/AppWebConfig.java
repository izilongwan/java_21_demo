package com.java_21_demo.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.java_21_demo.app.intercepter.DoIntercepter;

@Configuration
public class AppWebConfig implements WebMvcConfigurer {

    private final DoIntercepter doIntercepter;

    public AppWebConfig(DoIntercepter doIntercepter) {
        this.doIntercepter = doIntercepter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(doIntercepter).addPathPatterns("/**");
    }

}
