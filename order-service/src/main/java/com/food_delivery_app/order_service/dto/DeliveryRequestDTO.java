package com.food_delivery_app.order_service.dto;

import lombok.Data;

@Data
public class DeliveryRequestDTO {

    private Long orderId;
    private String address;
}
