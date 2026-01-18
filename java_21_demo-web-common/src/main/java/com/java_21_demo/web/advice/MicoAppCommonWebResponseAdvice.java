package com.java_21_demo.web.advice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java_21_demo.web.aspect.anno.WebResponseAnno;
import com.java_21_demo.web.config.property.WebResultBodyProperty;
import com.java_21_demo.web.domain.vo.ExceptionVO;
import com.java_21_demo.web.domain.vo.RVO;
import com.java_21_demo.web.util.HttpRequestUtil;

@RestControllerAdvice
public class MicoAppCommonWebResponseAdvice implements ResponseBodyAdvice<Object> {
    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    private final WebResultBodyProperty properties;

    public MicoAppCommonWebResponseAdvice(WebResultBodyProperty properties) {
        this.properties = properties;
    }

    static final List<Class<? extends Annotation>> RESPONSETYPES_LIST = Stream
            .of(ResponseBody.class, ExceptionHandler.class)
            .collect(Collectors.toList());

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Type genericResponseType = returnType.getGenericParameterType();

        if (genericResponseType instanceof Class<?>) {
            if ((Class<?>) genericResponseType == ExceptionVO.class) {
                return true;
            }
        }

        String name = returnType.getDeclaringClass().getName();
        String uri = HttpRequestUtil.getRequest().get().getRequestURI();

        if (isMatchPath(uri)) {
            return true;
        }

        WebResponseAnno webResponseAnno = returnType.getMethodAnnotation(WebResponseAnno.class);
        if (Objects.nonNull(webResponseAnno)) {
            return webResponseAnno.value();
        }

        webResponseAnno = returnType.getContainingClass().getAnnotation(WebResponseAnno.class);

        if (Objects.nonNull(webResponseAnno)) {
            return webResponseAnno.value();
        }

        Boolean b = Optional
                .ofNullable(Stream.of(returnType.getMethodAnnotations()).anyMatch(RESPONSETYPES_LIST::contains))
                .map(o -> o ? o : null)
                .orElse(Optional
                        .ofNullable(returnType.getContainingClass().getDeclaredAnnotation(RestController.class))
                        .map(Objects::nonNull)
                        .orElse(false));

        return b && properties.basePackage().stream().anyMatch(o -> name.startsWith(o));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {

        Long timecost = HttpRequestUtil.getAttribute("timecost").map(o -> o).map(o -> (Long) o).orElse(0L);

        if (body instanceof ExceptionVO) {
            ExceptionVO evo = (ExceptionVO) body;
            HttpStatus status = evo.getStatus();
            if (Objects.nonNull(status)) {
                response.setStatusCode(status);
            }
            return evo.getReason().toBuilder().timecost(timecost).build();
        }

        if (body instanceof Exception) {
            return RVO.error(null, ((Exception) body).getMessage());
        }

        Long total = HttpRequestUtil.getAttributeVoTotal().orElse(-1L);

        // 防止重复包裹的问题出现
        if (body instanceof RVO) {
            // 设置timecost
            return ((RVO<Object>) body).toBuilder().timecost(timecost).total(total).build();
        }

        RVO<Object> result = RVO.success(body, timecost, total);

        if (!(body instanceof String)) {
            return result;
        }

        try {
            // String类型不能直接包装，所以要进行些特别的处理
            // 将数据包装在RVO里后，再转换为json字符串响应给前端
            return new ObjectMapper().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return RVO.error(e.getMessage());
        }
    }

    private boolean isMatchPath(String path) {
        return properties.path().stream().anyMatch(p -> ANT_PATH_MATCHER.match(p, path));
    }
}
