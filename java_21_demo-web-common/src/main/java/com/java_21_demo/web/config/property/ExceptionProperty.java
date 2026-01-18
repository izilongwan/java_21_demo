package com.java_21_demo.web.config.property;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties("web-config.exception-config")
public record ExceptionProperty(
                @DefaultValue List<ExceptionStatus> exceptionList) {

        public static record ExceptionStatus(
                        Integer status,
                        @DefaultValue("-1") Integer code,
                        String message,
                        @DefaultValue List<String> classList) {

        }
}
