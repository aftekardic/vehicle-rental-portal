package com.vehicle_rental_portal.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle_rental_portal.backend.dto.VehicleDtos.VehicleDto;
import com.vehicle_rental_portal.backend.dto.VehicleDtos.VehicleSpecificRequestDto;
import com.vehicle_rental_portal.backend.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllVehicles(@RequestParam String param) {
        return null;
    }

    @PostMapping("/specific-all")
    public ResponseEntity<?> getAllSpecificVehicles(@RequestBody VehicleSpecificRequestDto vehicleSpecificRequestDto) {
        return vehicleService.allSpecificVehicles(vehicleSpecificRequestDto);
    }

    @GetMapping("/company-all")
    public ResponseEntity<?> getAllCompanyVehicles(@RequestParam String companyEmail) {
        return vehicleService.getAllCompanyVehicles(companyEmail);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVehicle(@RequestBody VehicleDto requestVehicle) {
        return vehicleService.addVehicle(requestVehicle);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id) {
        return vehicleService.deleteVehicle(id);
    }

}
