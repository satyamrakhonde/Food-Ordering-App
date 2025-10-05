package com.food_delivery_app.order_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequestDTO {

    private Long userId;
    private Long restaurantId;
    private List<OrderItemRequestDTO> items;
}
