package com.java_21_demo.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.java_21_demo.feign.client.fallback.FeignDemoAppFallback;

@FeignClient(value = "demo-app", fallback = FeignDemoAppFallback.class)
public interface FeignDemoAppClient {
    @GetMapping("/do/hello")
    String hello();

    @GetMapping("/do/env")
    String env();

    @GetMapping("/do/info")
    Object info();
}
