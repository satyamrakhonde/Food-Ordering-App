package com.food_delivery_app.order_service.service;

import com.food_delivery_app.order_service.dto.CreateOrderRequestDTO;
import com.food_delivery_app.order_service.dto.OrderResponseDTO;
import com.food_delivery_app.order_service.entity.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(CreateOrderRequestDTO request);

    OrderResponseDTO getOrderById(Long orderId);

    List<OrderResponseDTO> getOrdersByUserId(Long userId);

    OrderResponseDTO updateOrderStatus(Long id, OrderStatus status);

    OrderResponseDTO deleteOrder(Long id);
}
