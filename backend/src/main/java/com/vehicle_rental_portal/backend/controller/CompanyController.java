package com.vehicle_rental_portal.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle_rental_portal.backend.dto.CompanyDtos.CompanyDto;
import com.vehicle_rental_portal.backend.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("/info/{userEmail}")
    public ResponseEntity<?> getCompanyInformations(@PathVariable String userEmail) {
        return companyService.getCompanyInformations(userEmail);
    }

    @PutMapping("/update/{userEmail}")
    public ResponseEntity<?> updateCompany(@PathVariable String userEmail, @RequestBody CompanyDto companyDto) {
        return companyService.updateCompany(userEmail, companyDto);
    }
}
