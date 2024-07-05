package com.vehicle_rental_portal.backend.dto.UserDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakUserDto {
    private String name;
    private String surname;
    private String email;
    private String password;
}
