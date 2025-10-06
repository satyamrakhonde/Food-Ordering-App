package com.food_delivery_app.order_service.service;

import com.food_delivery_app.order_service.client.RestaurantClient;
import com.food_delivery_app.order_service.dto.*;
import com.food_delivery_app.order_service.entity.Order;
import com.food_delivery_app.order_service.entity.OrderItem;
import com.food_delivery_app.order_service.entity.OrderStatus;
import com.food_delivery_app.order_service.repository.OrderItemRepository;
import com.food_delivery_app.order_service.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;
    private final RestaurantClient restaurantClient;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ModelMapper modelMapper, RestaurantClient restaurantClient) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
        this.restaurantClient = restaurantClient;
    }

    @Override
    public OrderResponseDTO createOrder(CreateOrderRequestDTO request) {

        //Match all menu items from RestaurantService
        List<MenuItemResponseDTO> menuItems = restaurantClient.getMenuItems(request.getRestaurantId());

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setRestaurantId(request.getRestaurantId());
        order.setOrderStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());


        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for(OrderItemRequestDTO itemDTO : request.getItems()) {

            //Find menu item from RestaurantService
            MenuItemResponseDTO menuItem = menuItems.stream()
                    .filter(mi -> mi.getId().equals(itemDTO.getItemId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Menu item not found : " + itemDTO.getItemId()));

            if(!menuItem.isAvailable()) {
                throw new RuntimeException("Menu item not available: "+ menuItem.getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setItemId(menuItem.getId());
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(menuItem.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));
            orderItem.setOrder(order);

            totalAmount = totalAmount.add(orderItem.getSubTotal());
            orderItems.add(orderItem);
        }


        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);

        return modelMapper.map(savedOrder, OrderResponseDTO.class);
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
        return modelMapper.map(order, OrderResponseDTO.class);
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));

        order.setOrderStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        Order updatedOrder = orderRepository.save(order);

        return modelMapper.map(updatedOrder, OrderResponseDTO.class);
    }

    @Override
    public OrderResponseDTO deleteOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return null;
    }
}
