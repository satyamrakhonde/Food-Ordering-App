package com.food_delivery_app.order_service.service;

import com.food_delivery_app.common.dto.events.DeliveryAssignedEvent;
import com.food_delivery_app.order_service.entity.OrderStatus;
import com.food_delivery_app.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryAssignedConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "delivery-assigned-events", groupId = "order-group")
    private void consume(DeliveryAssignedEvent event) {
        log.info("Received DeliveryAssignedEvent for Order ID: {}", event.getOrderId());

        orderRepository.findById(event.getOrderId()).ifPresentOrElse(order -> {
            order.setOrderStatus(OrderStatus.valueOf(event.getStatus()));
            orderRepository.save(order);
            log.info("Order ID: {} updated to status: {}", event.getOrderId(), event.getStatus());
        }, () -> {
            log.warn("Order ID: {} not found for DeliveryAssignedEvent", event.getOrderId());
        });
    }
}
