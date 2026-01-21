package com.java_21_demo.feign.client.fallback;

import org.springframework.stereotype.Component;

import com.java_21_demo.feign.client.FeignDemoAppClient;

@Component
public class FeignDemoAppFallback implements FeignDemoAppClient {

    @Override
    public String hello() {
        return "feign-demo-app 服务不可用";
    }

    @Override
    public String env() {
        return "feign-demo-app 服务不可用";
    }

    @Override
    public Object info() {
        return "feign-demo-app 服务不可用";
    }

}
