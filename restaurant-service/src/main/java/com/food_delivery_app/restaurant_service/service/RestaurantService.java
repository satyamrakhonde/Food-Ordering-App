package com.food_delivery_app.restaurant_service.service;

import com.food_delivery_app.restaurant_service.entity.Restaurant;
import com.food_delivery_app.restaurant_service.repository.RestaurantRepository;
import com.food_delivery_app.restaurant_service.request.RestaurantRequestDTO;
import com.food_delivery_app.restaurant_service.response.RestaurantResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepo;

    @Autowired
    ModelMapper modelMapper;

    public RestaurantResponseDTO create(RestaurantRequestDTO request) {
        Restaurant restaurant = modelMapper.map(request, Restaurant.class);
        restaurant.setName(request.getName());
        restaurant.setAddress(restaurant.getAddress());
        restaurant.setContactNumber(restaurant.getContactNumber());
        restaurant.setOpenStatus(true);
//        restaurant.setCategory
        Restaurant saved = restaurantRepo.save(restaurant);
        return modelMapper.map(saved, RestaurantResponseDTO.class);
    }

    public List<RestaurantResponseDTO> getAll() {
        return restaurantRepo.findAll().stream()
                .map(restaurant -> modelMapper.map(restaurant, RestaurantResponseDTO.class))
                .collect(Collectors.toList());
    }

    public RestaurantResponseDTO getById(Long id) {
        Restaurant restaurant = restaurantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return modelMapper.map(restaurant, RestaurantResponseDTO.class);
    }

    public RestaurantResponseDTO update(Long id, @RequestBody RestaurantRequestDTO request) {
        Restaurant restaurantExisting = restaurantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        modelMapper.map(request, restaurantExisting); //updates only matching fields
        Restaurant updated = restaurantRepo.save(restaurantExisting);

        return modelMapper.map(updated, RestaurantResponseDTO.class);
    }

    public RestaurantResponseDTO delete(Long id) {
        Restaurant restaurantExisting = restaurantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        restaurantRepo.delete(restaurantExisting);
        return modelMapper.map(restaurantExisting, RestaurantResponseDTO.class);
    }
}
