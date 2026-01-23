package com.java_21_demo.rabbit_mq.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.java_21_demo.rabbit_mq.constants.MqConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MqListener {

	@RabbitListener(bindings = {@QueueBinding(
		value = @Queue(MqConstants.QUEUE_ORDER),
		exchange = @Exchange(name = MqConstants.EXCHANGE_ORDER, type = ExchangeTypes.TOPIC),
		key = {MqConstants.ROUTING_KEY_ORDER_ALL, MqConstants.ROUTING_KEY_COMMON_ALL})})
	public void order(Message message) {
		String payload = new String(message.getBody());
		log.info("Received order message: {}", payload);
	}

	@RabbitListener(queues = MqConstants.QUEUE_ORDER)
	public void orderMore(Message message) {
		String payload = new String(message.getBody());
		log.info("Received orderMore message: {}", payload);
	}

	@RabbitListener(bindings = {@QueueBinding(
		value = @Queue(MqConstants.QUEUE_ORDER2),
		exchange = @Exchange(name = MqConstants.EXCHANGE_ORDER, type = ExchangeTypes.TOPIC),
		key = {MqConstants.ROUTING_KEY_COMMON_ALL})})
	public void order2(Message message) {
		log.info("Received order2 message: {}", message);
	}

	@RabbitListener(queues = MqConstants.QUEUE_ORDER2)
	public void order2More(Message message) throws InterruptedException {
		Thread.sleep(500);
		log.info("Received order2More message: {}", message);
	}

	@RabbitListener(bindings = {@QueueBinding(
		value = @Queue(MqConstants.QUEUE_PAY),
		exchange = @Exchange(name = MqConstants.EXCHANGE_PAY, type = ExchangeTypes.TOPIC),
		key = {MqConstants.ROUTING_KEY_PAY_ALL, MqConstants.ROUTING_KEY_COMMON_ALL})})
	public void pay(Message message) {
		String payload = new String(message.getBody());
		log.info("Received pay message: {}", payload);
	}

	@RabbitListener(bindings = {@QueueBinding(
		value = @Queue(MqConstants.QUEUE_PAY2),
		exchange = @Exchange(name = MqConstants.EXCHANGE_PAY, type = ExchangeTypes.TOPIC),
		key = {MqConstants.ROUTING_KEY_PAY_COMMON})})
	public void pay2(Object obj) {
		log.info("Received pay2 message: {}", obj);
	}

	@RabbitListener(bindings = {@QueueBinding(
		value = @Queue(
			name = MqConstants.QUEUE_LAZY,
			arguments = {@Argument(
				name = MqConstants.ARGS_X_QUEUE_MODE,
				value = MqConstants.ARGS_X_QUEUE_MODE_LAZY)}),
		exchange = @Exchange(name = MqConstants.EXCHANGE_LAZY, type = ExchangeTypes.TOPIC),
		key = {MqConstants.ROUTING_KEY_LAZY})})
	public void lazy() {

	}

	@RabbitListener(bindings = {@QueueBinding(
		value = @Queue(
			name = MqConstants.QUEUE_DEADLETTER,
			arguments = {@Argument(
				name = MqConstants.ARGS_X_QUEUE_MODE,
				value = MqConstants.ARGS_X_QUEUE_MODE_LAZY)}),
		exchange = @Exchange(name = MqConstants.EXCHANGE_DEADLETTER, type = ExchangeTypes.TOPIC),
		key = {MqConstants.ROUTING_KEY_DEADLETTER})})
	public void deadletter() {

	}
}
