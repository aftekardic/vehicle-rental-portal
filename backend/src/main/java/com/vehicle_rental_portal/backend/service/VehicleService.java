package com.vehicle_rental_portal.backend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vehicle_rental_portal.backend.dto.VehicleDtos.VehicleDto;
import com.vehicle_rental_portal.backend.dto.VehicleDtos.VehicleResponseDto;
import com.vehicle_rental_portal.backend.dto.VehicleDtos.VehicleSpecificRequestDto;
import com.vehicle_rental_portal.backend.dto.VehicleDtos.VehicleSpecificResponseDto;
import com.vehicle_rental_portal.backend.entity.CompanyEntity;
import com.vehicle_rental_portal.backend.entity.ReservationEntity;
import com.vehicle_rental_portal.backend.entity.VehicleEntity;
import com.vehicle_rental_portal.backend.repository.CompanyRepository;
import com.vehicle_rental_portal.backend.repository.ReservationRepository;
import com.vehicle_rental_portal.backend.repository.VehicleRepository;
import com.vehicle_rental_portal.backend.util.ModelConverterUtil;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ModelConverterUtil modelConverter;

    public ResponseEntity<?> allSpecificVehicles(VehicleSpecificRequestDto vehicleSpecificRequestDto) {
        List<VehicleEntity> vehicles = vehicleRepository.findAvailableVehicles(
                vehicleSpecificRequestDto.getCity(),
                vehicleSpecificRequestDto.getType(),
                vehicleSpecificRequestDto.getStartDate(),
                vehicleSpecificRequestDto.getEndDate());

        List<VehicleSpecificResponseDto> vehicleDtos = new ArrayList<>();
        for (VehicleEntity vehicle : vehicles) {
            List<LocalDate> availableDates = vehicle.getAvailabilityDates();

            // Check for overlapping reservations
            List<ReservationEntity> overlappingReservations = reservationRepository.findAll().stream()
                    .filter(r -> r.getStartDate().isBefore(vehicleSpecificRequestDto.getEndDate()) &&
                            r.getEndDate().isAfter(vehicleSpecificRequestDto.getStartDate()))
                    .collect(Collectors.toList());

            for (ReservationEntity reservation : overlappingReservations) {
                LocalDate reservationStartDate = reservation.getStartDate();
                LocalDate reservationEndDate = reservation.getEndDate();

                availableDates = availableDates.stream()
                        .filter(date -> date.isBefore(reservationStartDate) || date.isAfter(reservationEndDate))
                        .collect(Collectors.toList());
            }

            if (!availableDates.isEmpty()) {
                VehicleSpecificResponseDto dto = new VehicleSpecificResponseDto();
                dto.setId(vehicle.getId());
                dto.setCompanyName(vehicle.getCompany().getName());
                dto.setCity(vehicle.getCompany().getCity());
                dto.setEmail(vehicle.getCompany().getEmail());
                dto.setType(vehicle.getType());
                dto.setDailyPrice(vehicle.getDailyPrice());
                dto.setAvailabilityDates(availableDates);
                dto.setAdditionalServices(vehicle.getAdditionalServices());
                vehicleDtos.add(dto);
            }
        }
        return ResponseEntity.ok().body(vehicleDtos);
    }

    public ResponseEntity<?> getAllCompanyVehicles(String companyEmail) {
        CompanyEntity company = companyRepository.findByEmail(companyEmail);

        List<VehicleEntity> vehicles = vehicleRepository.findByCompanyId(company.getId());

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
