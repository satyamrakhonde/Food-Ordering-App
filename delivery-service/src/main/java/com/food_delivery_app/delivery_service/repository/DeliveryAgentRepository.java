package com.food_delivery_app.delivery_service.repository;

import com.food_delivery_app.delivery_service.entity.DeliveryAgent;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryAgentRepository extends JpaRepository<DeliveryAgent, Long> {

//    @Lock(LockModeType.OPTIMISTIC)
//    @Query("SELECT a FROM DeliveryAgent a WHERE a.available = true ORDER BY a.id ASC") //picks only first element
//    List<DeliveryAgent> findFirstAvailable();

    Optional<DeliveryAgent> findFirstByAvailableTrueOrderByIdAsc();
}
