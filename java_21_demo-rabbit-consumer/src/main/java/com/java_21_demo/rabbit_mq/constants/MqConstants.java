package com.java_21_demo.rabbit_mq.constants;

public interface MqConstants {
    String ARGS_X_QUEUE_MODE = "x-queue-mode";
    String ARGS_X_QUEUE_MODE_LAZY = "lazy";

    String EXCHANGE_COMMON = "demo.app.exchange.common";
    String EXCHANGE_ORDER = "demo.app.exchange.order";
    String EXCHANGE_PAY = "demo.app.exchange.pay";
    String EXCHANGE_LAZY = "demo.app.exchange.lazy";
    String EXCHANGE_DEADLETTER = "demo.app.exchange.deadletter";
    String EXCHANGE_WAIT = "demo.app.exchange.wait";

    String QUEUE_ORDER = "demo.app.queue.order";
    String QUEUE_ORDER2 = "demo.app.queue.order2";
    String QUEUE_PAY = "demo.app.queue.pay";
    String QUEUE_PAY2 = "demo.app.queue.pay2";
    String QUEUE_LAZY = "demo.app.queue.lazy";
    String QUEUE_DEADLETTER = "demo.app.queue.deadletter";
    String QUEUE_WAIT = "demo.app.queue.wait";

    String ROUTING_KEY_ORDER_ALL = "demo.app.routing.key.order.#";
    String ROUTING_KEY_PAY_ALL = "demo.app.routing.key.pay.#";
    String ROUTING_KEY_COMMON_ALL = "demo.app.routing.key.common.*";
    String ROUTING_KEY_PAY_COMMON = "demo.app.routing.key.pay.common.*";
    String ROUTING_KEY_LAZY = "demo.app.routing.key.lazy.#";
    String ROUTING_KEY_DEADLETTER = "demo.app.routing.key.deadletter.#";
    String ROUTING_KEY_WAIT = "demo.app.routing.key.wait";
}
