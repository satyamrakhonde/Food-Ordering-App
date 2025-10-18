package com.food_delivery_app.delivery_service.service;

import com.food_delivery_app.common.dto.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCreatedConsumer {

    private final DeliveryService deliveryService;

    @KafkaListener(topics = "order-created-events", groupId = "delivery-group")
    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Received ORDER_CREATED_EVENT for order ID: {}", event.getOrderId());
        //Later we will deserialize it into a proper OrderCreatedEvent and create Delivery
        deliveryService.createDeliveryFromOrderEvent(event);
    }
}
