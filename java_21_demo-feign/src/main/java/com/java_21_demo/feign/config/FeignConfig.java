package com.java_21_demo.feign.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.java_21_demo.feign.service")
public class FeignConfig {

}
