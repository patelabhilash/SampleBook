package com.zksb.zksb.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "local-first-topic", groupId = "group-id")
    public void consumeMessage(String message) {
        System.out.println("Received message: " + message);
    }
}