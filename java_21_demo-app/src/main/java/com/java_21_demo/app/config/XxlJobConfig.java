package com.java_21_demo.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.java_21_demo.app.property.XxlJobProperty;
import com.java_21_demo.app.property.XxlJobProperty.Admin;
import com.java_21_demo.app.property.XxlJobProperty.Executor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class XxlJobConfig {
    private final XxlJobProperty xxlJobProperty;

    @Bean
    XxlJobSpringExecutor executor() {
        XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
        Executor executorProp = xxlJobProperty.executor();
        Admin admin = xxlJobProperty.admin();

        executor.setAdminAddresses(admin.addresses());
        executor.setAccessToken(admin.accessToken());
        executor.setAppname(executorProp.appname());
        executor.setIp(executorProp.ip());
        executor.setPort(executorProp.port());
        executor.setLogPath(executorProp.logpath());
        executor.setLogRetentionDays(executorProp.logretentiondays());
        return executor;
    }
}
