package com.api.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.api.kafka.domain.InterClubRequestDTO;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, InterClubRequestDTO> consumerFactory() {
	Map<String, Object> props = new HashMap<>();
	props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-cluster-kafka-bootstrap:9092");
	props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-interclub");
	props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
	props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
	props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.api.kafka.domain");
	return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
		new JsonDeserializer<>(InterClubRequestDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InterClubRequestDTO> kafkaListenerContainerFactory() {
	ConcurrentKafkaListenerContainerFactory<String, InterClubRequestDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(consumerFactory());
	factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
	factory.setConcurrency(3);
	return factory;
    }

}
