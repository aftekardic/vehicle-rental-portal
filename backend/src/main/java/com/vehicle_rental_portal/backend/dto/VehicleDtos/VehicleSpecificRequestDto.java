package com.vehicle_rental_portal.backend.dto.VehicleDtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleSpecificRequestDto {
    private String city;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;

}
