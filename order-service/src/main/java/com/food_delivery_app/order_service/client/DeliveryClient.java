package com.food_delivery_app.order_service.client;


import com.food_delivery_app.delivery_service.dto.DeliveryRequestDTO;
import com.food_delivery_app.delivery_service.dto.DeliveryResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "delivery-service", url = "http://localhost:8083/deliveries")
public interface DeliveryClient {

    @PostMapping("/deliveries")
    DeliveryResponseDTO assignDelivery(DeliveryRequestDTO request);
}
