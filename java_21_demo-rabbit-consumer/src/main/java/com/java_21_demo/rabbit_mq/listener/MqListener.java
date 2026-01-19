package com.java_21_demo.rabbit_mq.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MqListener {

	@RabbitListener(bindings = {@QueueBinding(value = @Queue("demo.app.queue.order"),
		exchange = @Exchange(name = "demo.app.exchange.order", type = ExchangeTypes.TOPIC),
		key = {"demo.app.routing.key.order.#", "demo.app.routing.key.common.*"})})
	public void order(Message message) {
		String payload = new String(message.getBody());
		log.info("Received order message: {}", payload);
	}

	@RabbitListener(bindings = {@QueueBinding(value = @Queue("demo.app.queue.order2"),
		exchange = @Exchange(name = "demo.app.exchange.order", type = ExchangeTypes.TOPIC),
		key = {"demo.app.routing.key.common.*"})})
	public void order2(Message message) {
		log.info("Received order2 message: {}", message);
	}

	@RabbitListener(bindings = {@QueueBinding(value = @Queue("demo.app.queue.pay"),
		exchange = @Exchange(name = "demo.app.exchange.pay", type = ExchangeTypes.TOPIC),
		key = {"demo.app.routing.key.pay.#", "demo.app.routing.key.common.*"})})
	public void pay(Message message) {
		String payload = new String(message.getBody());
		log.info("Received pay message: {}", payload);
	}

	@RabbitListener(bindings = {@QueueBinding(value = @Queue("demo.app.queue.pay2"),
		exchange = @Exchange(name = "demo.app.exchange.pay", type = ExchangeTypes.TOPIC),
		key = {"demo.app.routing.key.common.*"})})
	public void pay2(Object obj) {
		log.info("Received pay2 message: {}", obj);
	}
}
