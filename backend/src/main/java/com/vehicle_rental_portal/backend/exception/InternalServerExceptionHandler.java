package com.vehicle_rental_portal.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.vehicle_rental_portal.backend.dto.AuthDtos.AuthResponseDto;

public class InternalServerExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AuthResponseDto> handleRuntimeException(RuntimeException e) {
        AuthResponseDto response = AuthResponseDto.builder()
                .status("ERROR")
                .message("Internal Server Error: " + e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
