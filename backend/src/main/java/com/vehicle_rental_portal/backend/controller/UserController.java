package com.vehicle_rental_portal.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle_rental_portal.backend.dto.UserDtos.UserDto;
import com.vehicle_rental_portal.backend.service.UserService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info/{userEmail}")
    public ResponseEntity<?> getUserInformations(@PathVariable String userEmail) {
        return userService.getUserInformations(userEmail);
    }

    @PutMapping("/update/{userEmail}")
    public ResponseEntity<?> updateUser(@PathVariable String userEmail, @RequestBody UserDto userDto) {
        return userService.updateUser(userEmail, userDto);
    }
}
