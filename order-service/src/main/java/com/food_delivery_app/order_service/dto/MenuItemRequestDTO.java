package com.food_delivery_app.order_service.dto;

import lombok.*;

import java.math.BigDecimal;

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
    private BigDecimal price;

    private boolean available = true;

    private String description;
}
