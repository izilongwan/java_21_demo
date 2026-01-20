package com.java_21_demo.web.filter;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.java_21_demo.web.util.MicoAppCommonThreadLocalUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 请求生命周期过滤器：在响应后清理 ThreadLocal 中的用户上下文，避免线程复用时的数据污染。
 *
 * 执行顺序：本过滤器设置为 LOWEST_PRECEDENCE，确保其他过滤器先执行，然后在响应返回时清理。
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class WebCommonClearFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try {
            // 继续执行过滤链
            filterChain.doFilter(request, response);
        } finally {
            // 在 finally 块中清理 ThreadLocal，确保无论成功还是异常都会清理
            MicoAppCommonThreadLocalUtil.clear();
        }
    }
}
