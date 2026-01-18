package com.java_21_demo.web.config;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class MicoAppCommonJacksonConverterConfig {
    private static final DateTimeFormatter DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // 配置
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder
                    /*
                     * 序列化内容
                     * LocalDateTime -> String
                     * 服务端返回给客户端内容
                     */
                    .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_PATTERN))
                    .serializerByType(LocalDate.class, new LocalDateSerializer(DATE_PATTERN))
                    .serializerByType(Long.TYPE, LongJsonSerializer.instance)
                    .serializerByType(Long.class, LongJsonSerializer.instance)
                    /*
                     * 反序列化内容
                     * String -> Object
                     * 客户端传入服务端数据
                     */
                    .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_PATTERN))
                    .deserializerByType(LocalDate.class, new LocalDateDeserializer(DATE_PATTERN));
        };
    }

    /**
     * 数值转字符，用于返回前端数值过大导致精度不对
     * LongJsonSerializer
     */
    static class LongJsonSerializer extends JsonSerializer<Long> {

        final static LongJsonSerializer instance = new LongJsonSerializer(15);

        int limitCount;

        public LongJsonSerializer(int limitCount) {
            this.limitCount = limitCount;
        }

        @Override
        public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (Objects.isNull(value)) {
                gen.writeNull();
                return;
            }

            String string = value.toString();

            if (string.length() > limitCount) {
                gen.writeString(string);
                return;
            }

            gen.writeNumber(value);
        }

    }
}
