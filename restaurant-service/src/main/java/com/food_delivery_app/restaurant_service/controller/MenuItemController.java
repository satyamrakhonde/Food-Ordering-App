package com.food_delivery_app.restaurant_service.controller;

import com.food_delivery_app.restaurant_service.request.MenuItemRequestDTO;
import com.food_delivery_app.restaurant_service.response.MenuItemResponseDTO;
import com.food_delivery_app.restaurant_service.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/menu")
@RequiredArgsConstructor
public class MenuItemController {

    @Autowired
    MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemResponseDTO> addMenuItem(
            @PathVariable Long restaurantId,
            @RequestBody MenuItemRequestDTO request) {

        return new ResponseEntity<>(menuItemService.addMenuItem(restaurantId, request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDTO>> getMenuItemsByRestaurantId(@PathVariable Long restaurantId) {

        return new ResponseEntity<>(menuItemService.getMenuItems(restaurantId), HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<MenuItemResponseDTO> getMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long itemId) {

        return ResponseEntity.ok(menuItemService.getMenuItemById(itemId));
    }
}
