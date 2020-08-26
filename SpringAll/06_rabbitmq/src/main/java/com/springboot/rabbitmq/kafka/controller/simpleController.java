package com.springboot.rabbitmq.kafka.controller;

import com.springboot.rabbitmq.kafka.simple.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class simpleController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("/kafka/simple/{message}")
    public void sendMessga(@PathVariable("message") String message){
        kafkaProducer.sendMessage("penguin",message);
    }
}
