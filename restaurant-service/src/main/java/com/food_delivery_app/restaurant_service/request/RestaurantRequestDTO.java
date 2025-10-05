package com.food_delivery_app.restaurant_service.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantRequestDTO {

//    @NotBlank(message = "Restaurant name is required")
    private String name;

//    @NotBlank(message = "Address cannot be empty")
    private String address;

//    @Pattern(regexp = "^\\d{10}$", message = "Contact number must be 10 digits")
    private String contactNumber;

    private boolean openStatus = true;

    private String category;  // e.g. "Indian", "Italian"
}
