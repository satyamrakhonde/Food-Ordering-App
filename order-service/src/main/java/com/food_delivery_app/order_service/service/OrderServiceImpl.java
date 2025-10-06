package com.food_delivery_app.order_service.service;

import com.food_delivery_app.order_service.dto.CreateOrderRequestDTO;
import com.food_delivery_app.order_service.dto.OrderResponseDTO;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderResponseDTO createOrder(CreateOrderRequestDTO request) {
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setRestaurantId(request.getRestaurantId());
        order.setOrderStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        List<OrderItem> items = request.getItems().stream().map(itemReq -> {
            OrderItem item = new OrderItem();
            item.setItemId(item.getItemId());
            item.setQuantity(item.getQuantity());

            //TODO: Fetch actual price from Restaurant serivce(via Feign client later)
            item.setPrice(BigDecimal.valueOf(100));
            item.setSubTotal(item.getPrice().multiply(BigDecimal.valueOf(500))); //hard coded value for now
            item.setOrder(order);
            return item;
        }).toList();

        BigDecimal totalAmount = items.stream()
                .map(OrderItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalAmount);
        order.setItems(items);
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
