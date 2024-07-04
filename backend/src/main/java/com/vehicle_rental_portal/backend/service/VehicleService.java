package com.vehicle_rental_portal.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vehicle_rental_portal.backend.dto.VehicleDtos.VehicleDto;
import com.vehicle_rental_portal.backend.dto.VehicleDtos.VehicleResponseDto;
import com.vehicle_rental_portal.backend.entity.CompanyEntity;
import com.vehicle_rental_portal.backend.entity.VehicleEntity;
import com.vehicle_rental_portal.backend.repository.CompanyRepository;
import com.vehicle_rental_portal.backend.repository.VehicleRepository;
import com.vehicle_rental_portal.backend.util.ModelConverterUtil;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ModelConverterUtil modelConverter;

    public ResponseEntity<?> getAllCompanyVehicles(String companyEmail) {
        CompanyEntity company = companyRepository.findByEmail(companyEmail);

        List<VehicleEntity> vehicles = vehicleRepository.findByCompanyId(company.getId());

        // Entity listesini DTO listesine dönüştür
        List<VehicleResponseDto> vehicleDtos = vehicles.stream()
                .map(modelConverter::vehicleEntityToResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(vehicleDtos);
    }

    public ResponseEntity<?> addVehicle(VehicleDto vehicleRequest) {
        vehicleRepository.save(modelConverter.vehicleDtoToEntity(vehicleRequest));
        return ResponseEntity.ok().body(VehicleResponseDto.builder()
                .status("SUCCESS")
                .message("Vehicle added successfully")
                .build());

    }

    public ResponseEntity<?> deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
        return ResponseEntity.ok().body("Vehicle deleted successfully.");
    }
}
