package com.vehicle_rental_portal.backend.dto.AuthDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestAsCompanyDto {
    private String name;
    private String city;
    private String email;
    private String password;
}
