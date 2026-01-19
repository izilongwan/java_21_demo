package com.java_21_demo.web.config.property;

/**
 * MQ 消息队列配置属性
 *
 * 用于配置 RabbitMQ 的交换机、队列和路由信息。
 *
 * @param exchange 交换机名称
 * @param queue 队列名称，消息的存储位置
 * @param routingKey 路由键，用于交换机将消息路由到正确的队列
 */
public record MqProperty(String exchange, String queue, String[] routingKey) {

}
