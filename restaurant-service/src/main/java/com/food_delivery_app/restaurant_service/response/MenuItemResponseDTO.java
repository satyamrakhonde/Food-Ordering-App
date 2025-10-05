package com.food_delivery_app.restaurant_service.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemResponseDTO {

    private Long id;
    private String name;
    private Double price;
    private boolean available;
    private String description;
}
