package com.food_delivery_app.order_service.service;

import com.food_delivery_app.order_service.dto.CreateOrderRequestDTO;
import com.food_delivery_app.order_service.dto.OrderResponseDTO;
import jakarta.persistence.criteria.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Override
    public OrderResponseDTO createOrder(CreateOrderRequestDTO request) {
        return null;
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        return null;
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        return List.of();
    }

    @Override
    public OrderResponseDTO updateOrderStatus(Long id) {
        return null;
    }

    @Override
    public OrderResponseDTO deleteOrder(Long id) {
        return null;
    }
}
