package com.food_delivery_app.dto;

import lombok.Data;

@Data
public class DeliveryRequestDTO {

    private Long orderId;
    private String address;
}
