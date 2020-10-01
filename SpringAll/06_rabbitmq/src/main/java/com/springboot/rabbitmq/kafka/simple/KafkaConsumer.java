package com.springboot.rabbitmq.kafka.simple;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class KafkaConsumer {

    //简单消费者
    @KafkaListener(groupId = "test-consumer-group",topics = "penguin")
    public void consumer(ConsumerRecord<String, Object> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Consumer consumer){
        System.out.println("简单消费："+record.topic()+"-"+record.partition()+"-"+record.value());
    }


    @KafkaListener(groupId = "test-consumer-group",topics = "BatchAndManual")
    public void batchAndManualConsumer(ConsumerRecord<?, ?> record, Consumer consumer){

            System.out.printf("消费消息：topic=%s, partition=%d, offset=%d, key=%s, value=%s\n",
                    record.topic(), record.partition(), record.offset(), record.key(), record.value());
        consumer.commitSync();
    }
}
