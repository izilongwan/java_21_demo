package com.java_21_demo.web.config.property;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Web 响应体配置属性
 *
 * @param format      响应体格式化参数名称。示例: format
 * @param basePackage 需要包装响应体的基础包名集合。示例: com.java_21_demo.app
 * @param path        需要包装响应体的路径模式集合。支持 Ant 风格路径匹配。示例: /api/**, /admin/**
 */
@ConfigurationProperties(prefix = "web-config.result-body")
public record WebResultBodyProperty(
        @DefaultValue("format") String format,
        @DefaultValue Set<String> basePackage,
        @DefaultValue Set<String> path) {
}
