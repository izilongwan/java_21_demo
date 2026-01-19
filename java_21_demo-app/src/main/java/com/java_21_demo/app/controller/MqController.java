package com.java_21_demo.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java_21_demo.app.property.MqConfigProperty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequestMapping("/mq")
@RequiredArgsConstructor
public class MqController {
    private final MqConfigProperty mqConfigProperty;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping("send/{channel}/{key}/{count}")
    public String send(@PathVariable String channel, @PathVariable String key, @PathVariable int count,
        @RequestBody Object message) {
        for (int i = 0; i < count; i++) {
            rabbitTemplate.convertAndSend(channel, key, "%s %s %s".formatted(channel, message, i));
        }
        return "ok";
    }

    @GetMapping("config")
    public MqConfigProperty getMethodName() {
        return new MqConfigProperty(mqConfigProperty.order(), mqConfigProperty.pay());
    }

}
