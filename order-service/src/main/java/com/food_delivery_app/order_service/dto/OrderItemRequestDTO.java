package com.food_delivery_app.order_service.dto;

import lombok.Data;

@Data
public class OrderItemRequestDTO {

    private Long itemId;
    private int quantity;
}
