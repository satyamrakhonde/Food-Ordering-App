package com.food_delivery_app.delivery_service.controller;

import com.food_delivery_app.delivery_service.dto.DeliveryRequestDTO;
import com.food_delivery_app.delivery_service.dto.DeliveryResponseDTO;
import com.food_delivery_app.delivery_service.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<DeliveryResponseDTO> assignDelivery(@RequestBody DeliveryRequestDTO request) {
        return new ResponseEntity<>(deliveryService.assignDelivery(request), HttpStatus.OK);
    }

    @PutMapping("/{deliveryId}/status")
    public ResponseEntity<DeliveryResponseDTO> assignDelivery(@PathVariable Long deliveryId,
            @RequestParam String status) {
        return new ResponseEntity<>(deliveryService.updateDeliveryStatus(deliveryId, status), HttpStatus.OK);
    }

}
