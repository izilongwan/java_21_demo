package com.java_21_demo.web.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.java_21_demo.web.enums.PrivacyType;
import com.java_21_demo.web.interfaces.PrivacySerializer;
import com.java_21_demo.web.util.PrivacyEncryptSerializer;

/**
 * 隐私数据加密注解
 *
 * 用于标记需要进行隐私数据加密处理的方法或字段。
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JsonSerialize(using = PrivacyEncryptSerializer.class)
@JacksonAnnotationsInside
public @interface PrivacyEncrypt {
    /**
     * 指定加密类型
     *
     * @return 加密类型枚举
     */
    PrivacyType value() default PrivacyType.None;

    /**
     * 自定义隐私数据处理类
     *
     * @return 自定义隐私数据处理类
     */
    Class<? extends PrivacySerializer> serializer() default PrivacySerializer.class;
}
