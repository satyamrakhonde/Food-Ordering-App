package com.food_delivery_app.order_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemResponseDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private boolean available;
    private String description;
}
