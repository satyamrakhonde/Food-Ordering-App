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

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService{

    private final DeliveryRepository deliveryRepository;
    private final ModelMapper modelMapper;

    @Override
    public DeliveryResponseDTO assignDelivery(DeliveryRequestDTO request) {
//        Delivery delivery = modelMapper.map(request, Delivery.class);
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
    public DeliveryResponseDTO updateDeliveryStatus(Long deliveryId, String status) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery Not found"));

        delivery.setStatus(DeliveryStatus.valueOf(status));
        if(status.equals("DELIVERED")){
            delivery.setDeliveredAt(LocalDateTime.now());
        }
        Delivery updatedDelivery = deliveryRepository.save(delivery);
        return modelMapper.map(updatedDelivery, DeliveryResponseDTO.class);
    }
}
