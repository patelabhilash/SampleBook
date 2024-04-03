package com.sbkafkaweb.kafkaweb.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "local-first-topic")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}