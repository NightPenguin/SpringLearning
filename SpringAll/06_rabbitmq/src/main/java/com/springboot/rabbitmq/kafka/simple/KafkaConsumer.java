package com.springboot.rabbitmq.kafka.simple;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    //简单消费者
    @KafkaListener(groupId = "test-consumer-group",topics = "penguin")
    public void consumer(ConsumerRecord<String, Object> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Consumer consumer){
        System.out.println("简单消费："+record.topic()+"-"+record.partition()+"-"+record.value());
    }
}
