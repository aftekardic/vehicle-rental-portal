package com.vehicle_rental_portal.backend.dto.CompanyDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    private String name;
    private String city;
    private String email;
}
