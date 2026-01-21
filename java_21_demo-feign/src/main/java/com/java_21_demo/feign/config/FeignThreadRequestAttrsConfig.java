package com.java_21_demo.feign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestContextHolder;

@Configuration
public class FeignThreadRequestAttrsConfig {
    @Bean
    public TaskDecorator copyRequestAttrs() {
        return (runnable) -> {
            var attrs = RequestContextHolder.getRequestAttributes();
            return () -> {
                try {
                    RequestContextHolder.setRequestAttributes(attrs, true);
                    runnable.run();
                } finally {
                    RequestContextHolder.resetRequestAttributes();
                }
            };
        };
    }
}
