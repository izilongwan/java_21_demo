package com.java_21_demo.web.config;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.java_21_demo.web.config.property.ExceptionProperty;
import com.java_21_demo.web.domain.vo.ExceptionVO;
import com.java_21_demo.web.domain.vo.InputParamVO;
import com.java_21_demo.web.enums.MessageEnum;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
@Configuration
public class WebCommonExceptionConfig {

    private final ExceptionProperty exceptionStatusClassBo;

    @ExceptionHandler
    public ExceptionVO handleException(Exception e) {
        e.printStackTrace();

        String canonicalName = e.getClass().getCanonicalName();
        Set<ExceptionProperty.ExceptionStatus> esc = new HashSet<>();

        exceptionStatusClassBo.exceptionList().stream().anyMatch(o -> {
            boolean contains = o.classList().contains(canonicalName);
            if (contains) {
                esc.add(o);
            }
            return contains;
        });

        return esc.stream()
            .findAny()
            .map(o -> new ExceptionVO(
                Optional.ofNullable(o.message()).orElse(e.getMessage()),
                Optional.ofNullable(o.status()).map(HttpStatus::valueOf).orElse(HttpStatus.OK),
                o.code()))
            .orElse(ExceptionVO.error(e.getMessage()));
    }

    @ExceptionHandler(ExceptionVO.class)
    public ExceptionVO handleException(ExceptionVO evo) {
        return evo;
    }

    /**
     * <1> 处理 form data方式调用接口校验失败抛出的异常
     * <2> 处理 json 请求体调用接口校验失败抛出的异常
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ExceptionVO handleException(BindException e) {
        List<InputParamVO> list = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(o -> {
                String field = o.getObjectName().concat(".").concat(o.getField());
                String rule = o.getDefaultMessage();
                String message = String.format("%s: %s", field, rule);

                return InputParamVO.builder().field(field).rule(rule).message(message).build();
            })
            .collect(Collectors.toList());

        String message = list.stream().map(InputParamVO::getMessage).collect(Collectors.joining("; "));
        return ExceptionVO.error(list, message, MessageEnum.INPUT_INVALID_PARAM.getCode());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ExceptionVO handleException(HandlerMethodValidationException e) {
        List<InputParamVO> list = e.getAllValidationResults()
            .stream()
            .flatMap(res -> res.getResolvableErrors().stream().map(error -> {
                String field = res.getMethodParameter().getParameterName();
                String rule = error.getDefaultMessage();
                String message = String.format("%s: %s", field, rule);
                return InputParamVO.builder().field(field).rule(rule).message(message).build();
            }))
            .collect(Collectors.toList());

        String message = list.stream().map(InputParamVO::getMessage).collect(Collectors.joining("; "));
        return ExceptionVO.error(list, message, MessageEnum.INPUT_INVALID_PARAM.getCode());
    }

}
