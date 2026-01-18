package com.java_21_demo.web.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.java_21_demo.web.config.property.Knife4jProperty;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * Knife4j 接口文档配置
 */
@Configuration
@EnableConfigurationProperties(Knife4jProperty.class)
public class Knife4jConfig {

        @Bean
        public OpenAPI openAPI(Knife4jProperty properties) {
                return new OpenAPI()
                                .info(new Info()
                                                .title(properties.title())
                                                .description(properties.description())
                                                .version(properties.version())
                                                .contact(new Contact()
                                                                .name(properties.contact().name())
                                                                .email(properties.contact().email())
                                                                .url(properties.contact().url()))
                                                .license(new License()
                                                                .name(properties.license().name())
                                                                .url(properties.license().url())));
        }
}
