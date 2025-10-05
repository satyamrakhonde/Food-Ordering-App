package com.food_delivery_app.restaurant_service.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemRequestDTO {

//    @NotBlank(message = "Item name is required")
    private String name;

//    @NotNull(message = "Price cannot be null")
//    @Positive(message = "Price must be greater than 0")
    private Double price;

    private boolean available = true;

    private String description;
}
