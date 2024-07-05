package com.vehicle_rental_portal.backend.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle_rental_portal.backend.dto.UserDtos.KeycloakUserDto;
import com.vehicle_rental_portal.backend.service.KeycloakUserService;

@RestController
@RequestMapping("/kc")
public class KeycloakUserController {
    @Autowired
    private KeycloakUserService keycloakUserService;

    @PutMapping("/updateUser/{userEmail}")
    public ResponseEntity<?> updateKcUser(@PathVariable String userEmail,
            @RequestBody KeycloakUserDto keycloakUserDto, HttpServletRequest servletRequest) {
        return keycloakUserService.updateKcUser(userEmail, keycloakUserDto, servletRequest);
    }
}
