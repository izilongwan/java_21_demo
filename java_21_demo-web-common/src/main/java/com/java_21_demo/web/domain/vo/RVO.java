package com.java_21_demo.web.domain.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.java_21_demo.web.enums.MessageEnum;
import com.java_21_demo.web.util.HttpRequestUtil;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Builder(toBuilder = true)
@Data
@JacksonXmlRootElement
public class RVO<T> {

    @Tolerate
    public RVO() {
    }

    public static <T> RVO<T> success(T data, Long timecost, Long total) {
        return auto(data, MessageEnum.SUCCESS, timecost, total);
    }

    public static <T> RVO<T> success(T data) {
        return auto(data, MessageEnum.SUCCESS, -1L, -1L);
    }

    public static <T> RVO<T> error(T data, String message, int code) {
        return auto(data, message, code);
    }

    public static <T> RVO<T> error(T data) {
        return error(data, MessageEnum.ERROR);
    }

    public static <T> RVO<T> error(T data, MessageEnum messageEnum) {
        return error(data, messageEnum.getMessage(), messageEnum.getCode());
    }

    public static <T> RVO<T> error(T data, String message) {
        return auto(data, message, MessageEnum.ERROR.getCode());
    }

    public static <T> RVO<T> auto(T data, String message, Integer code) {
        return auto(data, message, code, -1L, -1L);
    }

    public static <T> RVO<T> auto(T data, MessageEnum messageEnum, Long timecost, Long total) {
        return auto(data, messageEnum.getMessage(), messageEnum.getCode(), timecost, total);
    }

    public static <T> RVO<T> auto(T data, MessageEnum messageEnum) {
        return auto(data, messageEnum.getMessage(), messageEnum.getCode(), -1L, -1L);
    }

    public static <T> RVO<T> auto(T data, String message, Integer code, Long timecost, Long total) {
        return RVO.<T>builder()
                .code(code)
                .data(data)
                .message(message)
                .timecost(timecost)
                .total(total)
                .path(HttpRequestUtil.getRequest().map(o -> o.getRequestURI()).orElse(null))
                .query(getQuery())
                .build();
    }

    public static String decodeQuery(String query) {
        try {
            return URLDecoder.decode(query, StandardCharsets.ISO_8859_1.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T> String getQuery() {
        return HttpRequestUtil.getRequest()
                .map(o -> Optional.ofNullable(o.getQueryString())
                        .map(RVO::decodeQuery)
                        .orElse(null))
                .orElse(null);
    }

    public boolean isSuccess() {
        return getCode() == MessageEnum.SUCCESS.getCode();
    }

    Integer code;

    T data;

    String message;

    final Long timestamp = System.currentTimeMillis();

    Long timecost;

    Long total;

    String path;

    String query;
}
