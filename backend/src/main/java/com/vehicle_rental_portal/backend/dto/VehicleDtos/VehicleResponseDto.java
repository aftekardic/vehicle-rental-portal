package com.vehicle_rental_portal.backend.dto.VehicleDtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponseDto {
    private Long id;
    private Long companyId;
    private String type;
    private BigDecimal dailyPrice;
    private List<LocalDate> availabilityDates;
    private List<String> additionalServices;
    private String status;
    private String message;
}
