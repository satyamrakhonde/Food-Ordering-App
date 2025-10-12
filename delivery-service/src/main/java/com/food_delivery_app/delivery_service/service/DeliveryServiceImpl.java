package com.food_delivery_app.delivery_service.service;

import com.food_delivery_app.delivery_service.dto.DeliveryRequestDTO;
import com.food_delivery_app.delivery_service.dto.DeliveryResponseDTO;
import com.food_delivery_app.delivery_service.entity.Delivery;
import com.food_delivery_app.delivery_service.entity.DeliveryStatus;
import com.food_delivery_app.delivery_service.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    @Override
    public DeliveryResponseDTO assignDelivery(DeliveryRequestDTO request) {
//        Delivery delivery = modelMapper.map(request, Delivery.class);

        //Check if a delivery already exsits for this orderID
        Optional<Delivery> existingDelivery = deliveryRepository.findByOrderId(request.getOrderId());
        if(existingDelivery.isPresent()) {
            System.out.println("Delivery already exists for orderId: " + request.getOrderId());
            return modelMapper.map(existingDelivery.get(), DeliveryResponseDTO.class);
        }

        Delivery delivery = Delivery.builder()
                .orderId(request.getOrderId())
                .deliveryAgentId(10L) // dummy agent for now
                .address(request.getAddress())
                .status(DeliveryStatus.ASSIGNED)
                .assignedAt(LocalDateTime.now())
                .build();

        Delivery savedDelivery = deliveryRepository.save(delivery);

        return modelMapper.map(savedDelivery, DeliveryResponseDTO.class);
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
