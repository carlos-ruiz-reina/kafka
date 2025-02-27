package com.api.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
public class KafkaApplication {

    public static void main(String[] args) {
	log.info("Kafka API service started");
	SpringApplication.run(KafkaApplication.class, args);
    }

}
