package com.food_delivery_app.restaurant_service.controller;

import com.food_delivery_app.restaurant_service.request.RestaurantRequestDTO;
import com.food_delivery_app.restaurant_service.response.RestaurantResponseDTO;
import com.food_delivery_app.restaurant_service.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@RequestBody RestaurantRequestDTO request) {
        return new ResponseEntity<>(restaurantService.create(request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants() {
//        List<RestaurantResponse> response = restaurantService.getAll()
//                .stream()
//                .map(RestaurantMapper::toResponse)
//                .collect(Collectors.toList());
        return new ResponseEntity<>(restaurantService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(@PathVariable Long id) {
        return new ResponseEntity<>(restaurantService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantRequestDTO request) {

//        Restaurant updatedEntity = RestaurantMapper.toEntity(request);
//        Restaurant updated = restaurantService.update(id, updatedEntity);

        return new ResponseEntity<>(restaurantService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> deleteRestaurant(@PathVariable Long id) {

        return new ResponseEntity<>(restaurantService.delete(id), HttpStatus.OK);
    }
}
