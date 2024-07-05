package com.vehicle_rental_portal.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vehicle_rental_portal.backend.dto.CompanyDtos.CompanyDto;
import com.vehicle_rental_portal.backend.entity.CompanyEntity;
import com.vehicle_rental_portal.backend.repository.CompanyRepository;
import com.vehicle_rental_portal.backend.util.ModelConverterUtil;

@Service
public class CompanyService {
    @Autowired
    private ModelConverterUtil modelConverter;
    @Autowired
    private CompanyRepository companyRepository;

    public ResponseEntity<?> getCompanyInformations(String userEmail) {
        CompanyDto companyDto = modelConverter.companyEntityToDto(companyRepository.findByEmail(userEmail));
        return ResponseEntity.ok().body(companyDto);

    }

    public ResponseEntity<?> updateCompany(String userEmail, CompanyDto companyDto) {
        CompanyEntity currentCompany = companyRepository.findByEmail(userEmail);
        currentCompany.setEmail(companyDto.getEmail());
        currentCompany.setName(companyDto.getName());
        currentCompany.setCity(companyDto.getCity());
        companyRepository.save(currentCompany);
        return ResponseEntity.ok().body("Company informations was updated.");
    }
}
