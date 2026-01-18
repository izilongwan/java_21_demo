package com.java_21_demo.web.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestUtil {
    public static final String USER = "X-USER";
    private static final String VO_TOTAL = "#TOTAL#";
    private static final String AUTH_START = "Bearer ";

    public static Optional<HttpServletRequest> setUser(Object user) {
        return setAttribute(USER, user);
    }

    public static Optional<Object> getUser() {
        return getAttribute(USER);
    }

    public static Optional<ServletRequestAttributes> getRequestAttributes() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(o -> (ServletRequestAttributes) o);
    }

    public static Optional<HttpServletRequest> getRequest() {
        return getRequestAttributes().map(o -> o.getRequest());
    }

    public static Optional<HttpServletRequest> setAttribute(String name, Object value) {
        return getRequest().map(o -> {
            o.setAttribute(name, value);
            return o;
        });
    }

    public static Optional<HttpServletRequest> setAttributeVOTotal(Long total) {
        return getRequest().map(o -> {
            o.setAttribute(VO_TOTAL, total);
            return o;
        });
    }

    public static Optional<Long> getAttributeVoTotal() {
        return getRequest().map(o -> (Long) o.getAttribute(VO_TOTAL));
    }

    public static Optional<Object> getAttribute(String name) {
        return getRequest().map(o -> o.getAttribute(name));
    }

    public static Optional<Map<String, String[]>> getParameterMapArr() {
        return getRequest().map(o -> o.getParameterMap());
    }

    public static Optional<String> getParameter(String name) {
        return getRequest().map(o -> o.getParameter(name));
    }

    public static Optional<Map<String, Object>> getParameterMap() {
        return getParameterMapArr()
                .map(o -> {
                    Map<String, Object> map = new HashMap<String, Object>();
                    return o
                            .entrySet()
                            .stream()
                            .reduce(map, (p, c) -> {
                                p.put(c.getKey(), c.getValue()[0]);
                                return p;
                            }, (a, b) -> null);
                });
    }

    public static Optional<String> getBearerToken() {
        return getRequest()
                .map(o -> o.getHeader(HttpHeaders.AUTHORIZATION))
                .map(o -> o.startsWith(AUTH_START) ? o.substring(AUTH_START.length()) : null);
    }
}
