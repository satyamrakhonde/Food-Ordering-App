package com.food_delivery_app.restaurant_service.service;

import com.food_delivery_app.restaurant_service.entity.Restaurant;
import com.food_delivery_app.restaurant_service.repository.RestaurantRepository;
import com.food_delivery_app.restaurant_service.request.RestaurantRequestDTO;
import com.food_delivery_app.restaurant_service.response.RestaurantResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepo;

    @Autowired
    ModelMapper modelMapper;

    public RestaurantResponse create(RestaurantRequestDTO request) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getName());
        restaurant.setAddress(restaurant.getAddress());
        restaurant.setContactNumber(restaurant.getContactNumber());
        restaurant.setOpenStatus(true);
//        restaurant.set
    }

    public List<RestaurantResponse> getAll() {
    }

    public RestaurantResponse getById(Long id) {
    }

    public RestaurantResponse update(Long id) {
    }
}
