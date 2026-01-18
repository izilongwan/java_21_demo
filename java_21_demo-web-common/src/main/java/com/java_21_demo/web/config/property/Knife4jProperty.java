package com.java_21_demo.web.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Knife4j 配置属性
 *
 * @param title       API 文档生成的标题
 * @param description API 文档的详细描述信息
 * @param version     API 的版本号
 * @param contact     API 维护者的联系人信息，对应 {@link Contact}
 * @param license     API 使用的许可证信息，对应 {@link License}
 */
@ConfigurationProperties(prefix = "web-config.knife4j")
public record Knife4jProperty(
        String title,
        String description,
        String version,
        @DefaultValue Contact contact,
        @DefaultValue License license) {

    /**
     * 联系人信息
     *
     * @param name  联系人姓名或团队名称
     * @param email 联系人电子邮箱地址
     * @param url   联系人相关的网站或服务地址
     */
    public static record Contact(String name, String email, String url) {
    }

    /**
     * 许可证信息
     *
     * @param name 许可证的完整名称（如：Apache 2.0）
     * @param url  许可证内容的官方链接地址
     */
    public static record License(String name, String url) {
    }
}
