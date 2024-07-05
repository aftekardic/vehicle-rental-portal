package com.vehicle_rental_portal.backend.dto.ReservationDtos;

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
public class ReservedDto {
    private Long id;

    private Long userId;
    private String userName;
    private String userSurname;
    private String userEmail;

    private Long vehicleId;
    private String vehicleType;
    private BigDecimal vehicleDailyPrice;
    private List<LocalDate> vehicleAvailabilityDates;
    private List<String> vehicleAdditionalServices;

    private Long companyId;
    private String companyName;
    private String companyCity;
    private String companyEmail;

    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
