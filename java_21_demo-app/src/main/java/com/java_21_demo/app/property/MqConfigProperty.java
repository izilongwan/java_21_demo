package com.java_21_demo.app.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.java_21_demo.web.config.property.MqProperty;

@ConfigurationProperties(prefix = "demo.app.property.mq.config")
public record MqConfigProperty(MqProperty order, MqProperty pay) {

}
