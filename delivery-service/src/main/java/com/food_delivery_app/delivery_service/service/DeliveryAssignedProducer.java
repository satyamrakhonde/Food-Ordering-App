package com.food_delivery_app.delivery_service.service;

import com.food_delivery_app.common.dto.events.DeliveryAssignedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryAssignedProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "delivery-assigned-events";

    public void sendDeliveryAssignedEvent(DeliveryAssignedEvent event) {
        log.info("Publishing DeliveryAssignedEvent for order ID: {}", event.getOrderId());
        kafkaTemplate.send(TOPIC, event);
    }
}
