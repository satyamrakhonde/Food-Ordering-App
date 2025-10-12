package com.food_delivery_app.delivery_service.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
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
    private Long id;
    private String name;

    private Boolean available = true;

    @Version
    private Long version;   //Used by JPA for optmistic locking
}
