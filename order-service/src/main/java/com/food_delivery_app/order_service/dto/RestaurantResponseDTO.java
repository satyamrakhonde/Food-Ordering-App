package com.food_delivery_app.order_service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponseDTO {

    private Long id;
    private String name;
    private String address;
    private String contactNumber;
    private boolean openStatus;
    private String category;
    private List<MenuItemResponseDTO> menuItems;
}
