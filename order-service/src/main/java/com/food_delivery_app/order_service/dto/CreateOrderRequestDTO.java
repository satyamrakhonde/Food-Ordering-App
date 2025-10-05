package com.food_delivery_app.order_service.dto;

import lombok.Data;

@Data
public class CreateOrderRequestDTO {

    private Long userId;
    private Long restaurantId;
    private Long List<OrderItemRequestDTO> items;
}
