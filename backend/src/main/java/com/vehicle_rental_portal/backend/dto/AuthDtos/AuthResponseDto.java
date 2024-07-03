package com.vehicle_rental_portal.backend.dto.AuthDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
    private String status;
    private String message;
    private String accessToken;
    private String refreshToken;
}
