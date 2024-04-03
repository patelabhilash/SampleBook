package com.sbkafkaweb.kafkaweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sbkafkaweb.kafkaweb.service.KafkaConsumerService;
import com.sbkafkaweb.kafkaweb.service.KafkaProducerService;

@RestController
public class MainController {

    final String localFirstTopic = "local-first-topic";

    @Autowired
    KafkaProducerService kafkaProducerService;

    @Autowired
    KafkaConsumerService kafkaConsumerService;

    @PostMapping("/")
    public void defaultPost(@RequestBody String msg) {
        System.out.println(msg);
    }

    @PostMapping("/produce")
    public void produceMessage(@RequestBody String msg) {
        System.out.println(msg);
        kafkaProducerService.sendMessage(localFirstTopic, msg);
    }

    @GetMapping("/findbyid/{id}")
    public String getMessageById(@PathVariable long id) {
        return id + "";
    }

    @GetMapping("/consume")
    public String getMessageById() {
        kafkaConsumerService.listen("msg");
        return "finished";
    }

    @DeleteMapping("/delete")
    public void deleteMessage() {
        System.out.println("delete api");
    }

}
