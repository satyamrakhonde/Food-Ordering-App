package com.food_delivery_app.delivery_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;

    @Column(nullable = false)
    private Boolean available = true;

    @Version
    private Long version;   //Used by JPA for optmistic locking
}
