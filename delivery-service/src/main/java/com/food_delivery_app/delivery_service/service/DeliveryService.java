package com.food_delivery_app.delivery_service.service;

import com.food_delivery_app.delivery_service.dto.DeliveryRequestDTO;
import com.food_delivery_app.delivery_service.dto.DeliveryResponseDTO;

public interface DeliveryService {
    public DeliveryResponseDTO assignDelivery(DeliveryRequestDTO request);

    public DeliveryResponseDTO updateDeliveryStatus(Long deliveryId, String status);
}
