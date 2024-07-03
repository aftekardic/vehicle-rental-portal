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
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<AuthResponseDto> handleAccessDeniedException(AccessDeniedException e) {
        AuthResponseDto response = AuthResponseDto.builder()
                .status("FORBIDDEN")
                .message("You do not have a permission for this proccess.")
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AuthResponseDto> handleRuntimeException(RuntimeException e) {
        AuthResponseDto response = AuthResponseDto.builder()
                .status("ERROR")
                .message("Internal Server Error: " + e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
