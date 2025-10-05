package com.food_delivery_app.order_service.controller;

import com.food_delivery_app.order_service.dto.CreateOrderRequestDTO;
import com.food_delivery_app.order_service.dto.OrderResponseDTO;
import com.food_delivery_app.order_service.dto.UpdateOrderStatusRequest;
import com.food_delivery_app.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    //1. Create Order
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTO request) {
        return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.CREATED);
    }

    //2. Get Order By ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    // 3. Get All Orders for a User
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(orderService.getOrdersByUserId(userId), HttpStatus.OK);
    }

    //4. Update Order Status (PATCH)
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequest request) {
        return new ResponseEntity<>(orderService.updateOrderStatus(id), HttpStatus.OK);
    }

    // 5. Delete Order
    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> deleteOrder(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.deleteOrder(id), HttpStatus.OK);
    }
}
