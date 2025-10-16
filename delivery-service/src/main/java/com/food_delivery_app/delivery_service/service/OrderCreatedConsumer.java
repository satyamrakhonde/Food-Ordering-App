package com.food_delivery_app.delivery_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderCreatedConsumer {

    @KafkaListener(topics = "order-created-events", groupId = "delivery-group")
    public void consumeOrderCreatedEvent(Object message) {
        log.info("Received ORDER_CREATED event: {}", message);
        //Later we will deserialize it into a proper OrderCreatedEvent and create Delivery
    }
}
