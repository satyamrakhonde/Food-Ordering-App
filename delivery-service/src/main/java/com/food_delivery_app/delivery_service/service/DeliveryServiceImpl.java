package com.food_delivery_app.delivery_service.service;

import com.food_delivery_app.delivery_service.dto.DeliveryRequestDTO;
import com.food_delivery_app.delivery_service.dto.DeliveryResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService{
    @Override
    public DeliveryResponseDTO assignDelivery(DeliveryRequestDTO request) {
        return null;
    }

    @Override
    public DeliveryResponseDTO updateDeliveryStatus(Long orderId, String status) {
        return null;
    }
}
