package com.java_21_demo.rabbit_mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WaitQueueConfig {
    @Bean
    TopicExchange waitExchange() {
        return ExchangeBuilder.topicExchange("demo.app.exchange.wait")
            .durable(true)
            .build();
    }

    @Bean
    Queue waitQueue() {
        return QueueBuilder.durable("demo.app.queue.wait")
            .lazy()
            .ttl(10_000)
            .deadLetterExchange("demo.app.exchange.deadletter")
            .deadLetterRoutingKey("demo.app.routing.key.deadletter.#")
            .build();
    }

    @Bean
    Binding bindQueue() {
        return BindingBuilder.bind(waitQueue())
            .to(waitExchange())
            .with("demo.app.routing.key.wait");
    }
}
