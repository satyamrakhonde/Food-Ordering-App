package com.food_delivery_app.delivery_service.service;

import com.food_delivery_app.common.dto.events.DeliveryAssignedEvent;
import com.food_delivery_app.common.dto.events.OrderCreatedEvent;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
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

    @Autowired
    private DeliveryAssignedProducer deliveryAssignedProducer;

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

               //Added after delivery status is updated
               if(agent == null) {
                   log.warn("No available agents - creating pending deliveries for order Id {}", request.getOrderId());
                   Delivery pending = Delivery.builder()
                           .orderId(request.getOrderId())
                           .deliveryAddress(request.getAddress())
                           .status(DeliveryStatus.PENDING)
                           .build();
                   deliveryRepository.save(pending);
                   throw new ApiException("No agents available, delivery pending assignment", HttpStatus.SERVICE_UNAVAILABLE);
               }

               //Step 3: Mark as unavailable (this triggers version check)
               agent.setAvailable(false);
               deliveryAgentRepository.save(agent);

               //Step 4: Create new Delivery
//               Delivery delivery = Delivery.builder()
//                       .orderId(request.getOrderId())
////                       .deliveryAgentId(agent.getId())
////                       .deliveryAgent.getId(agent.getId())
//                       .address(request.getAddress())
//                       .status(DeliveryStatus.ASSIGNED)
//                       .assignedAt(LocalDateTime.now())
//                       .build();

               /// I have changed the above implementation of builder to this below -test it
                Delivery delivery = new Delivery();
                delivery.setOrderId(request.getOrderId());
                DeliveryAgent deliveryAgent = new DeliveryAgent();
                deliveryAgent.setId(agent.getId());
                deliveryAgent.setName(agent.getName());
                delivery.setDeliveryAgent(deliveryAgent);
                Delivery savedDelivery = deliveryRepository.save(delivery);

               log.info("Delivery created successfully with agentId: {}", savedDelivery.getDeliveryAgent().getId());
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
                .orElseThrow(() -> new ApiException("Delivery not found", HttpStatus.NOT_FOUND));

        DeliveryStatus status = DeliveryStatus.valueOf(newStatus.toUpperCase());
        delivery.setStatus(status);

        if(status == DeliveryStatus.DELIVERED) {
            delivery.setDeliveredAt(LocalDateTime.now());

            DeliveryAgent agent = deliveryAgentRepository.findById(delivery.getDeliveryAgent().getId())
                    .orElseThrow(() -> new ApiException("Agent not found", HttpStatus.NOT_FOUND));

            agent.setAvailable(true);
            deliveryAgentRepository.save(agent);

            log.info("Agent {} marked available again", agent.getId());
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

    @Override
    public void createDeliveryFromOrderEvent(OrderCreatedEvent event) {
        log.info("Creating delivery for order ID:  {}", event.getOrderId());

        //1. Prevent duplicate deliveries for the same order
        if(deliveryRepository.findByOrderId(event.getOrderId()).isPresent()) {
            log.warn("Delivery already exists for Order ID: {}", event.getOrderId());
            return;
        }

        //2. Create new delivery record
        Delivery delivery = new Delivery();
        delivery.setOrderId(event.getOrderId());
        delivery.setUserId(event.getUserId());
        delivery.setRestaurantId(event.getRestaurantId());
        delivery.setStatus(DeliveryStatus.PENDING);
        delivery.setCreatedAt(LocalDateTime.now());


        //3. Assign available agent (concurrency-safe)
        Optional<DeliveryAgent> availableAgent = deliveryAgentRepository.findFirstByAvailableTrueOrderByIdAsc();

        if(availableAgent.isPresent()) {
            DeliveryAgent agent = availableAgent.get();
            agent.setAvailable(false);
            delivery.setDeliveryAgent(agent);
            delivery.setStatus(DeliveryStatus.ASSIGNED);
            delivery.setDeliveryAddress(delivery.getDeliveryAddress());
            delivery.setAssignedAt(LocalDateTime.now());

            deliveryAgentRepository.save(agent);
            log.info("Assigned agent {} to order {}", agent.getId(), event.getOrderId());
        } else {
            log.warn("No available agent, marking delivery as PENDING");
            delivery.setStatus(DeliveryStatus.PENDING);
        }

        //4. Save Delivery
        deliveryRepository.save(delivery);
        log.info("Delivery created successfully for order {} with status {}",
                event.getOrderId(), delivery.getStatus());

        //Publish DeliveryAssignedEvent
        DeliveryAssignedEvent assignedEvent = new DeliveryAssignedEvent(
                delivery.getOrderId(),
                delivery.getId(),
                delivery.getDeliveryAgent().getId(),
                delivery.getStatus().name()
        );

        deliveryAssignedProducer.sendDeliveryAssignedEvent(assignedEvent);
        log.info("DeliveryAssignedEvent published for order ID: {}", delivery.getOrderId());
    }
}
