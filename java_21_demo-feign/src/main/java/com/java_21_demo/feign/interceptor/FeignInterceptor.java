package com.java_21_demo.feign.interceptor;

import java.util.Objects;
import java.util.Optional;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;

public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

        if (Objects.isNull(attrs)) {
            return;
        }

        HttpServletRequest request = attrs.getRequest();

        // 1. 透传 Header，排除系统级 Header 避免冲突
        request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
            if (!isExcludedHeader(headerName)) {
                String headerValue = request.getHeader(headerName);
                if (headerValue != null) {
                    requestTemplate.header(headerName, headerValue);
                }
            }
        });

        // 2. 安全透传 Query String，处理 ? 和 & 拼接逻辑
        Optional.ofNullable(request.getQueryString())
            .filter(StringUtils::hasLength)
            .ifPresent(queryString -> {
                String currentUrl = requestTemplate.url();
                String separator = currentUrl.contains("?") ? "&" : "?";
                requestTemplate.uri(currentUrl + separator + queryString);
            });
    }

    private boolean isExcludedHeader(String headerName) {
        return headerName.equalsIgnoreCase("content-length")
            || headerName.equalsIgnoreCase("host")
            || headerName.equalsIgnoreCase("connection");
    }

}
