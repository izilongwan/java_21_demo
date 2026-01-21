package com.java_21_demo.feign.decoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.stereotype.Component;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            // 1. 获取下游返回的 Body 字符串
            String body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));

            // 2. 尝试解析成通用的 Result 对象（假设下游也用这个结构）
            Map<String, Object> map = objectMapper.readValue(body, Map.class);
            String message = (String)map.getOrDefault("message", "未知微服务异常");
            Integer code = (Integer)map.getOrDefault("code", response.status());
            // 3. 返回你自己的业务异常
            // 这样你的 GlobalExceptionHandler 就能像处理本地异常一样处理它了
            return new RuntimeException(String.format("Feign 调用异常，路径：%s, 状态码：%s，消息：%s", methodKey, code, message));

        } catch (IOException e) {
            // 如果解析失败，回退到默认 FeignException
            return FeignException.errorStatus(methodKey, response);
        }
    }

}
