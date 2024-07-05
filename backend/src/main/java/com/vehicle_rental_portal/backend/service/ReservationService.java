package com.vehicle_rental_portal.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vehicle_rental_portal.backend.dto.ReservationDtos.ReservationBookRequestDto;
import com.vehicle_rental_portal.backend.dto.ReservationDtos.ReservationDto;
import com.vehicle_rental_portal.backend.dto.ReservationDtos.ReservedDto;
import com.vehicle_rental_portal.backend.entity.ReservationEntity;
import com.vehicle_rental_portal.backend.entity.UserEntity;
import com.vehicle_rental_portal.backend.entity.VehicleEntity;
import com.vehicle_rental_portal.backend.repository.ReservationRepository;
import com.vehicle_rental_portal.backend.repository.UserRepository;
import com.vehicle_rental_portal.backend.repository.VehicleRepository;
import com.vehicle_rental_portal.backend.util.ModelConverterUtil;

@Service
public class ReservationService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ModelConverterUtil modelConverter;

    public ResponseEntity<?> bookAvailableVehicle(ReservationBookRequestDto reservationBookRequestDto) {

        VehicleEntity vehicle = vehicleRepository.findById(reservationBookRequestDto.getVehicle_id()).get();
        UserEntity user = userRepository.findByEmail(reservationBookRequestDto.getUser_email());

        ReservationDto reservationDto = ReservationDto.builder().user_id(user.getId())
                .vehicle_id(reservationBookRequestDto.getVehicle_id()).status("")
                .startDate(reservationBookRequestDto.getStart_date()).endDate(reservationBookRequestDto.getEnd_date())
                .build();
        reservationRepository.save(modelConverter.reservationDtoToEntity(reservationDto));
        return ResponseEntity.ok().body(vehicle.getCompany().getName() + "'s vehicle was reserved.");
    }

    public ResponseEntity<?> deleteReservedVehicle(Long id) {
        reservationRepository.deleteById(id);
        return ResponseEntity.ok().body("Reservation was cancelled.");
    }

    public ResponseEntity<?> getReservedVehicle(String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail);
        List<ReservationEntity> reservations = reservationRepository.findByUserId(user.getId());
        List<ReservedDto> reservedDtos = reservations.stream()
                .map(modelConverter::reservationsEntityToDtos)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(reservedDtos);
    }

}
