package com.food_delivery_app.common.dto.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAssignedEvent {
    private Long orderId;
    private Long deliveryId;
    private Long deliveryAgentId;
    private String status; //e.g OUT_FOR_DELIVERY
}
