package com.api.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.api.kafka.domain.InterClubRequestDTO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
	this.kafkaTemplate = kafkaTemplate;
    }

    public void publishMessage(String topic, InterClubRequestDTO request) {
	log.info("Data publish " + request);
	kafkaTemplate.send(topic, request);
    }

}
