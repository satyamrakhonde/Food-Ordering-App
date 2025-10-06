package com.food_delivery_app.order_service.client;

import com.food_delivery_app.order_service.dto.MenuItemRequestDTO;
import com.food_delivery_app.order_service.dto.MenuItemResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "restaurant-service", url = "http://localhost:8081")
public interface RestaurantClient {

    @GetMapping("/restaurants/{id}/menu")
    List<MenuItemResponseDTO> getMenuItems(@PathVariable Long id);
}
