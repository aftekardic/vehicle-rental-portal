package com.vehicle_rental_portal.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle_rental_portal.backend.dto.ReservationDtos.ReservationBookRequestDto;
import com.vehicle_rental_portal.backend.service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reserved")
    public ResponseEntity<?> getReservedVehicle(@PathVariable String userEmail) {
        return reservationService.getReservedVehicle(userEmail);
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookAvailableVehicle(@RequestBody ReservationBookRequestDto reservationBookRequestDto) {
        return reservationService.bookAvailableVehicle(reservationBookRequestDto);
    }

}
