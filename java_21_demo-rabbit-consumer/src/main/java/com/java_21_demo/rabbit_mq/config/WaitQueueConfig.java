package com.java_21_demo.rabbit_mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.java_21_demo.rabbit_mq.constants.MqConstants;

@Configuration
public class WaitQueueConfig {
    @Bean
    TopicExchange waitExchange() {
        return ExchangeBuilder.topicExchange(MqConstants.EXCHANGE_WAIT)
            .durable(true)
            .build();
    }

    @Bean
    Queue waitQueue() {
        return QueueBuilder.durable(MqConstants.QUEUE_WAIT)
            .lazy()
            .ttl(10_000)
            .deadLetterExchange(MqConstants.EXCHANGE_DEADLETTER)
            .deadLetterRoutingKey(MqConstants.ROUTING_KEY_DEADLETTER)
            .build();
    }

    @Bean
    Binding bindQueue() {
        return BindingBuilder.bind(waitQueue())
            .to(waitExchange())
            .with(MqConstants.ROUTING_KEY_WAIT);
    }
}
