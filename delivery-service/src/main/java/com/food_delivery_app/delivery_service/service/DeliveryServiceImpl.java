package com.food_delivery_app.delivery_service.service;

import com.food_delivery_app.delivery_service.dto.DeliveryRequestDTO;
import com.food_delivery_app.delivery_service.dto.DeliveryResponseDTO;
import com.food_delivery_app.delivery_service.entity.Delivery;
import com.food_delivery_app.delivery_service.entity.DeliveryAgent;
import com.food_delivery_app.delivery_service.entity.DeliveryStatus;
import com.food_delivery_app.delivery_service.exception.ApiException;
import com.food_delivery_app.delivery_service.repository.DeliveryAgentRepository;
import com.food_delivery_app.delivery_service.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService{

    private final DeliveryRepository deliveryRepository;
    private final ModelMapper modelMapper;
    private final DeliveryAgentRepository deliveryAgentRepository;

    private static final Logger log = LoggerFactory.getLogger(DeliveryServiceImpl.class);

    @Override
    @Transactional
    public DeliveryResponseDTO assignDelivery(DeliveryRequestDTO request) {
        log.info("Creating delivery for orderId: {}", request.getOrderId());

       int maxRetries = 3;
       for(int attempt = 1; attempt <= maxRetries; attempt++) {

           try{
               //Step 1 : Check if a delivery already exsits for this orderID
               Optional<Delivery> existingDelivery = deliveryRepository.findByOrderId(request.getOrderId());
               if(existingDelivery.isPresent()) {
                   throw new ApiException("Delivery already Exists for this order", HttpStatus.CONFLICT);
//                   return modelMapper.map(existingDelivery.get(), DeliveryResponseDTO.class);
               }

               //Step 2: Find Available agent
               DeliveryAgent agent = deliveryAgentRepository.findFirstByAvailableTrueOrderByIdAsc()
                       .orElseThrow(() -> new ApiException("No available agent at the moment", HttpStatus.SERVICE_UNAVAILABLE));

               //Step 3: Mark as unavailable (this triggers version check)
               agent.setAvailable(false);
               deliveryAgentRepository.save(agent);

               //Step 4: Create new Delivery
               Delivery delivery = Delivery.builder()
                       .orderId(request.getOrderId())
                       .deliveryAgentId(agent.getId())
                       .address(request.getAddress())
                       .status(DeliveryStatus.ASSIGNED)
                       .assignedAt(LocalDateTime.now())
                       .build();

               Delivery savedDelivery = deliveryRepository.save(delivery);

               log.info("Delivery created successfully with agentId: {}", savedDelivery.getDeliveryAgentId());
               return modelMapper.map(savedDelivery, DeliveryResponseDTO.class);

           } catch (OptimisticLockingFailureException e) {
               System.out.println("Conflict detected (Attempt" + attempt + ") , retrying...");
               if(attempt == maxRetries) {
                   throw new RuntimeException("Failed to assign agent after multiple retries");
               }
               //small delay before retrying
               try{
                   Thread.sleep(100L * attempt);
               } catch (InterruptedException ignored) {}
           }
       }
       throw new RuntimeException("Unable to assign delivery agent");
    }

    @Override
    public DeliveryResponseDTO updateDeliveryStatus(Long deliveryId, String newStatus) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery Not found"));

        delivery.setStatus(DeliveryStatus.valueOf(newStatus));
        if(newStatus.equals("DELIVERED")){
            delivery.setDeliveredAt(LocalDateTime.now());
        }
        Delivery updatedDelivery = deliveryRepository.save(delivery);
        return modelMapper.map(updatedDelivery, DeliveryResponseDTO.class);
    }

    @Override
    public DeliveryResponseDTO getDeliveryByDeliveryId(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        return modelMapper.map(delivery, DeliveryResponseDTO.class);
    }

    @Override
    public DeliveryResponseDTO getDeliveryByOrderId(Long orderId) {
        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Delivery not found for orderId: " + orderId));
        return modelMapper.map(delivery, DeliveryResponseDTO.class);
    }

    @Override
    public List<DeliveryResponseDTO> getDeliveriesByAgentId(Long agentId) {
        List<Delivery> deliveries = deliveryRepository.findByDeliveryAgentId(agentId);
        return deliveries.stream()
                .map(delivery -> modelMapper.map(delivery, DeliveryResponseDTO.class))
                .collect(Collectors.toList());
    }
}
