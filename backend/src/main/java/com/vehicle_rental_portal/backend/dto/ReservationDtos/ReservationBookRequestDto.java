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
public class ReservationBookRequestDto {
    private Long vehicle_id;
    private String user_email;
    private LocalDate start_date;
    private LocalDate end_date;
}
