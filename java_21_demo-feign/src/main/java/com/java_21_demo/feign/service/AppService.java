package com.java_21_demo.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("demo-app")
public interface AppService {
    @GetMapping("/do/hello")
    String hello();

    @GetMapping("/do/env")
    String env();

    @GetMapping("/do/info")
    Object info();
}
