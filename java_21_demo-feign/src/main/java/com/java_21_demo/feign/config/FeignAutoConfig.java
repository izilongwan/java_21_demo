package com.java_21_demo.feign.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.java_21_demo.feign.client.fallback.FeignDemoAppFallback;
import com.java_21_demo.feign.decoder.FeignErrorDecoder;
import com.java_21_demo.feign.interceptor.FeignInterceptor;

import feign.Feign;

@Configuration
@ConditionalOnClass({Feign.class})
@Import({
    FeignErrorDecoder.class,
    FeignThreadRequestAttrsConfig.class,
    FeignInterceptor.class,
    FeignDemoAppFallback.class})
public class FeignAutoConfig {

}
