package com.food_delivery_app.restaurant_service.request;

//import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class CategoryRequestDTO {
    private String name;
    private String description;
}
