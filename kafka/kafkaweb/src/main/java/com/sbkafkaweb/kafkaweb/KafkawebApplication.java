package com.sbkafkaweb.kafkaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class KafkawebApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkawebApplication.class, args);
	}

}
