package com.java_21_demo.app.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "xxl.job")
public record XxlJobProperty(
    Admin admin,
    Executor executor) {

    public record Admin(
        String addresses,
        String accessToken) {}

    public record Executor(
        String appname,
        String ip,
        int port,
        String logpath,
        int logretentiondays) {}
}
