package com.example.springbootkafka;

import com.example.springbootkafka.config.KafkaConfigProps;
import com.example.springbootkafka.domain.CustomerVisit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
public class SpringbootKafkaApplication {

	@Autowired
	private ObjectMapper objectMapper;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootKafkaApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(final KafkaTemplate<String,String> kafkaTemplate, KafkaConfigProps kafkaConfigProps) throws JsonProcessingException {
		final CustomerVisit customerVisit = CustomerVisit.builder()
				.customerId(UUID.randomUUID().toString())
				.dateTime(LocalDateTime.now())
				.build();

		final String payload = objectMapper.writeValueAsString(customerVisit);

		return args -> {
			kafkaTemplate.send(kafkaConfigProps.getTopic(), payload);
		};
	}

	@KafkaListener(topics = "jbeanny-books-v1",groupId = "books-v1")
	public String kafkListener(final String message) {
		System.out.println(message + " 🤦‍♂️🤦‍♂️");
		return message;
	}
}
