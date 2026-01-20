package com.java_21_demo.web.intercepter;

import java.util.Optional;

import org.springframework.web.servlet.HandlerInterceptor;

import com.java_21_demo.web.util.MicoAppCommonThreadLocalUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WebCommonAuthIntercepter implements HandlerInterceptor {

    private static final String X_USER = "x-user";

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        MicoAppCommonThreadLocalUtil.remove(X_USER);
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        Optional.ofNullable(request.getHeader(X_USER))
            .map(o -> MicoAppCommonThreadLocalUtil.set(X_USER, o));
        return true;
    }

}
