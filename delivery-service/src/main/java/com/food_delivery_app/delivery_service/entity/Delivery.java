package com.food_delivery_app.delivery_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long orderId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long restaurantId;

//  Delivery agent assigned (nullable until assigned)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_agent_id")
    private DeliveryAgent deliveryAgent;

    // Delivery address — optional, we can fetch later from user service
    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime assignedAt;
    private LocalDateTime deliveredAt;
}
