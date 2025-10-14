package com.food_delivery_app.delivery_service.controller;

import com.food_delivery_app.delivery_service.common.ResponseDTO;
import com.food_delivery_app.delivery_service.dto.DeliveryRequestDTO;
import com.food_delivery_app.delivery_service.dto.DeliveryResponseDTO;
import com.food_delivery_app.delivery_service.entity.DeliveryStatus;
import com.food_delivery_app.delivery_service.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<ResponseDTO<DeliveryResponseDTO>> assignDelivery(@RequestBody DeliveryRequestDTO request) {
        DeliveryResponseDTO response = deliveryService.assignDelivery(request);

//        ResponseDTO<DeliveryResponseDTO> responseDTO =
//         new ResponseDTO<>("Delivery Assigned Successfully", response);
//
//        return ResponseEntity.ok(responseDTO);

        return new ResponseEntity<>(new ResponseDTO<>("Delivery Assigned Successfully", response), HttpStatus.OK);
    }

    @PutMapping("/{deliveryId}/status")
    public ResponseEntity<ResponseDTO<DeliveryResponseDTO>> assignDelivery(@PathVariable Long deliveryId,
            @RequestParam String newStatus) {
        DeliveryResponseDTO response = deliveryService.updateDeliveryStatus(deliveryId, newStatus);
        return new ResponseEntity<>(new ResponseDTO<>("Delivery status updated Successfully", response), HttpStatus.OK);
    }


    @GetMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponseDTO> getDeliveriesByDeliveryId(@PathVariable Long deliveryId) {
        return new ResponseEntity<>(deliveryService.getDeliveryByDeliveryId(deliveryId), HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<DeliveryResponseDTO> getDeliveriesByOrderId(@PathVariable Long orderId) {
        return new ResponseEntity<>(deliveryService.getDeliveryByOrderId(orderId), HttpStatus.OK);
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<DeliveryResponseDTO>> getDeliveriesByAgentId(@PathVariable Long agentId) {
        return new ResponseEntity<>(deliveryService.getDeliveriesByAgentId(agentId), HttpStatus.OK);
    }

}
