package com.java_21_demo.database.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 数据库自动配置聚合器
 *
 * 聚合所有数据库相关配置:
 * - MyBatis Plus 配置
 * - Redis 配置
 */
@AutoConfiguration
@Import({
        MicoAppDatabaseMybatisPlusConfig.class,
        MicoAppDatabaseRedisConfig.class
})
public class DatabaseAutoConfigurationAggregator {
}
