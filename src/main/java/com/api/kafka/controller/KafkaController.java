package com.api.kafka.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.kafka.domain.InterClubRequestDTO;
import com.api.kafka.producer.KafkaProducer;

@RestController
@RequestMapping("/tail")
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    public KafkaController(KafkaProducer kafkaProducer) {
	this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/publish")
    public String publishMessage(@RequestParam String topic, @RequestBody InterClubRequestDTO request) {
	kafkaProducer.publishMessage(topic, request);
	return "Mensaje enviado al topic " + topic;
    }

}
