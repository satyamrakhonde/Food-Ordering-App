//package com.food_delivery_app.restaurant_service.service;
//
//import com.food_delivery_app.restaurant_service.event.OrderCreatedEvent;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class OrderEventConsumer {
//
//    @KafkaListener(topics = "order-created", groupId = "restaurant-group")
//    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {
//        System.out.println("[Restaurant-Service] New order received!");
//        System.out.println("Order ID: " + event.getOrderId());
//        System.out.println("Restaurant ID: " + event.getRestaurantId());
//        System.out.println("Total Amount: " + event.getTotalAmount());
//    }
//}
