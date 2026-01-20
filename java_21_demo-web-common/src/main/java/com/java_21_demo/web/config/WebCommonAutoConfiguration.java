package com.java_21_demo.web.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import com.java_21_demo.web.advice.MicoAppCommonWebResponseAdvice;
import com.java_21_demo.web.aspect.MicoAppCommonWebResponseAspect;
import com.java_21_demo.web.config.property.AmqpConfig;
import com.java_21_demo.web.config.property.ExceptionProperty;
import com.java_21_demo.web.config.property.WebResultBodyProperty;
import com.java_21_demo.web.filter.MicoAppCommonClearFilter;

/**
 * Web 公共组件自动装配聚合配置
 * 统一管理所有需要自动装配的配置类
 */
@Configuration
@EnableConfigurationProperties({WebResultBodyProperty.class, Knife4jProperties.class, ExceptionProperty.class})
@Import({
    MicoAppCommonWebResponseAdvice.class,
    MicoAppCommonWebResponseAspect.class,
    MicoAppCommonJacksonConverterConfig.class,
    MicoAppCommonWebExceptionConfig.class,
    MicoAppCommonValidatorConfig.class,
    MicoAppCommonWebMvcConfig.class,
    MicoAppCommonClearFilter.class,
    Knife4jConfig.class,
    AmqpConfig.class
})
public class WebCommonAutoConfiguration {

    /**
     * 集中的自动装配配置入口
     * 所有 web-common 相关的配置都在此聚合
     */
}
