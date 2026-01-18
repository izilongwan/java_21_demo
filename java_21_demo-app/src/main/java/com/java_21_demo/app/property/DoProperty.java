package com.java_21_demo.app.property;

import lombok.Data;
import lombok.experimental.Accessors;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data // 使用 Lombok 提供 Getter/Setter
@Component // 必须作为组件才能被 @RefreshScope 处理
@RefreshScope // 开启热更新
@Accessors(chain = true) // 启用链式调用的 Setter
@ConfigurationProperties(prefix = "property.do")
public class DoProperty {

    private Info info;
    private Info[] girls;

    @Data
    public static class Info {
        private String name;
        private int age;
        private String gender;
    }
}
