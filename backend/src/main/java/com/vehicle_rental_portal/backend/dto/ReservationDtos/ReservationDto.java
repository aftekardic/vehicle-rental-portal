package com.vehicle_rental_portal.backend.dto.ReservationDtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

    private Long user_id;

    private Long vehicle_id;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;
}
