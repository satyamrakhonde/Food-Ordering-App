package com.food_delivery_app.delivery_service.dto;

import com.food_delivery_app.delivery_service.entity.DeliveryStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryResponseDTO {

    private Long id;
    private Long orderId;
    private Long deliveryAgentId;
    private DeliveryStatus status;
    private String address;
    private LocalDateTime assignedAt;
    private LocalDateTime deliveredAt;
}
