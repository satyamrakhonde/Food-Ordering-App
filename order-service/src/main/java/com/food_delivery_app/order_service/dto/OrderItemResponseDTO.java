package com.food_delivery_app.order_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponseDTO {
    private Long itemId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subTotal;
}
