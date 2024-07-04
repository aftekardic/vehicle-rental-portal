package com.vehicle_rental_portal.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import com.vehicle_rental_portal.backend.dto.AuthDtos.AuthResponseDto;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<AuthResponseDto> handleHttpClientErrorException(HttpClientErrorException e) {
        AuthResponseDto response = AuthResponseDto.builder()
                .status("UNAUTHORIZED")
                .message("User is not found in the system!")
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<AuthResponseDto> handleAccessDeniedException(AccessDeniedException e) {
        AuthResponseDto response = AuthResponseDto.builder()
                .status("FORBIDDEN")
                .message("You do not have a permission for this proccess.")
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

    }
}
