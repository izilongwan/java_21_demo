package com.java_21_demo.web.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import com.java_21_demo.web.advice.WebCommonResponseAdvice;
import com.java_21_demo.web.aspect.aop.WebCommonWebResponseAop;
import com.java_21_demo.web.config.property.AmqpConfig;
import com.java_21_demo.web.config.property.ExceptionProperty;
import com.java_21_demo.web.config.property.WebResultBodyProperty;
import com.java_21_demo.web.filter.WebCommonClearFilter;

/**
 * Web 公共组件自动装配聚合配置
 * 统一管理所有需要自动装配的配置类
 */
@Configuration
@EnableConfigurationProperties({WebResultBodyProperty.class, Knife4jProperties.class, ExceptionProperty.class})
@Import({
    WebCommonResponseAdvice.class,
    WebCommonWebResponseAop.class,
        WebCommonJacksonConverterConfig.class,
    WebCommonExceptionConfig.class,
    CommonValidatorConfig.class,
    WebCommonMvcConfig.class,
    WebCommonClearFilter.class,
    WebCommonKnife4jConfig.class,
    AmqpConfig.class
})
public class WebCommonAutoConfiguration {

    /**
     * 集中的自动装配配置入口
     * 所有 web-common 相关的配置都在此聚合
     */
}
