package com.api.kafka.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.api.kafka.domain.InterClubPricingDTO;
import com.api.kafka.domain.InterClubRequestDTO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class KafkaConsumer {

    @Autowired
    private Environment env;

    @KafkaListener(topics = "interclub", groupId = "group-interclub", containerFactory = "kafkaListenerContainerFactory", properties = {
	    "spring.json.value.default.type=com.api.kafka.domain.InterClubRequestDTO" })
    public void consume(InterClubRequestDTO request, Acknowledgment acknowledgment,
	    @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) throws InterruptedException {
	try {
	    log.info(request.toString());
	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("X-DB-Env", request.getHeaders().getBbddKey());
	    headers.set("x-api-key", request.getHeaders().getXApiKey());
	    headers.set("Authorization", request.getHeaders().getToken());
	    String url = env.getProperty("insurances.gateway.url") + env.getProperty("insurances.controller.endpoint")
		    + env.getProperty("insurances.receipt.interclub.endpoint");
	    HttpEntity<InterClubPricingDTO> entity = new HttpEntity<InterClubPricingDTO>(request.getRequest(), headers);
	    ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, entity, Class.class);
	    if (response.getStatusCode().equals(HttpStatus.OK)) {
		acknowledgment.acknowledge();
	    } else {
		log.error(response.getStatusCode() + ": " + response.getBody().toString());
		acknowledgment.acknowledge();
	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	    acknowledgment.acknowledge();
	}
    }

}
