package com.food_delivery_app.delivery_service.repository;

import com.food_delivery_app.delivery_service.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
//    Optional<Delivery> findById(Long orderId);
    List<Delivery> findByDeliveryAgentId(Long agentId);

    Optional<Delivery> findByOrderId(Long orderId);
}
