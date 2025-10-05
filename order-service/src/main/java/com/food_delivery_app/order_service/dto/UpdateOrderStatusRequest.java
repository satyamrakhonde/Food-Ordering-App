package com.food_delivery_app.order_service.dto;

import com.food_delivery_app.order_service.entity.OrderStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusRequest {
    private OrderStatus status;
}
