package com.food_delivery_app.order_service.service;

import com.food_delivery_app.order_service.dto.CreateOrderRequestDTO;
import com.food_delivery_app.order_service.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(CreateOrderRequestDTO request);

    OrderResponseDTO getOrderById(Long id);

    List<OrderResponseDTO> getOrdersByUserId(Long userId);

    OrderResponseDTO updateOrderStatus(Long id);

    OrderResponseDTO deleteOrder(Long id);
}
