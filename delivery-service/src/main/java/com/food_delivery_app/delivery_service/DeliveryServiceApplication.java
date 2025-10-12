package com.food_delivery_app.delivery_service;

import com.food_delivery_app.delivery_service.entity.DeliveryAgent;
import com.food_delivery_app.delivery_service.repository.DeliveryAgentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DeliveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner loadAgents(DeliveryAgentRepository repo) {
		return args -> {
			if (repo.count() == 0) {
				repo.saveAll(List.of(
						new DeliveryAgent(101L, "Ramesh", true, 0L),
						new DeliveryAgent(102L, "Suresh", true, 0L),
						new DeliveryAgent(103L, "Mahesh", true, 0L)
				));
				System.out.println("✅ Loaded 3 test delivery agents");
			}
		};
	}

}
