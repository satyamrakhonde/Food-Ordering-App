package com.food_delivery_app.delivery_service.service;

import com.food_delivery_app.common.dto.events.OrderCreatedEvent;
import com.food_delivery_app.delivery_service.dto.DeliveryRequestDTO;
import com.food_delivery_app.delivery_service.dto.DeliveryResponseDTO;
import com.food_delivery_app.delivery_service.entity.DeliveryStatus;

import java.util.List;

public interface DeliveryService {
    public DeliveryResponseDTO assignDelivery(DeliveryRequestDTO request);

    public DeliveryResponseDTO updateDeliveryStatus(Long deliveryId, String newStatus);

    DeliveryResponseDTO getDeliveryByDeliveryId(Long deliveryId);

    DeliveryResponseDTO getDeliveryByOrderId(Long orderId);

    List<DeliveryResponseDTO> getDeliveriesByAgentId(Long agentId);

    void createDeliveryFromOrderEvent(OrderCreatedEvent event);
}
