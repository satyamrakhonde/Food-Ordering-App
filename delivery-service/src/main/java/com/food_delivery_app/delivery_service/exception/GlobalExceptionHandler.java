package com.food_delivery_app.delivery_service.exception;

import com.food_delivery_app.delivery_service.common.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ResponseDTO<Object>> handleApiException(ApiException ex) {
        ResponseDTO<Object> response = new ResponseDTO<>(
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, ex.getStatus());
    }
}
