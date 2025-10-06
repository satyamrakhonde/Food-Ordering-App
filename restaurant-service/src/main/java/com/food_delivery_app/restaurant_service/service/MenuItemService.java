package com.food_delivery_app.restaurant_service.service;

import com.food_delivery_app.restaurant_service.entity.MenuItem;
import com.food_delivery_app.restaurant_service.entity.Restaurant;
import com.food_delivery_app.restaurant_service.repository.MenuItemRepository;
import com.food_delivery_app.restaurant_service.repository.RestaurantRepository;
import com.food_delivery_app.restaurant_service.request.MenuItemRequestDTO;
import com.food_delivery_app.restaurant_service.response.MenuItemResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    MenuItemRepository menuItemRepo;

    @Autowired
    RestaurantRepository restaurantRepository;

    public MenuItemResponseDTO addMenuItem(Long restaurantId, MenuItemRequestDTO request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with ID:" + restaurantId));

        MenuItem menuItem = modelMapper.map(request, MenuItem.class);
        menuItem.setName(request.getName());
        menuItem.setPrice(request.getPrice());
        menuItem.setDescription(request.getDescription());

        menuItem.setRestaurant(restaurant); //set relation both sides
        menuItem = menuItemRepo.save(menuItem);

        //maintain bidirectional consistency
        restaurant.getMenuItems().add(menuItem);
        restaurantRepository.save(restaurant);

        return modelMapper.map(menuItem, MenuItemResponseDTO.class);
    }

    public List<MenuItemResponseDTO> getMenuItems(Long restaurantId) {
        Optional<MenuItem> menuItem = menuItemRepo.findById(restaurantId);

        return menuItem.stream()
                .map(item ->
                        modelMapper.map(item, MenuItemResponseDTO.class))
                .toList();
    }

    public MenuItemResponseDTO getMenuItemById(Long itemId) {
        MenuItem menuItem = menuItemRepo.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        return modelMapper.map(menuItem, MenuItemResponseDTO.class);
    }
}
