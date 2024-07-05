package com.vehicle_rental_portal.backend.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vehicle_rental_portal.backend.dto.UserDtos.KeycloakUserDto;
import com.vehicle_rental_portal.backend.util.KeycloakUtil;

@Service
public class KeycloakUserService {
    @Value("${keycloak.update-url}")
    private String kcUpdateUrl;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KeycloakUtil keycloakUtil;

    public ResponseEntity<?> updateKcUser(String userEmail, KeycloakUserDto keycloakUserDto,
            HttpServletRequest servletRequest) {

        String authorization = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, authorization);
        String userKeycloakId = keycloakUtil.getUserSub(userEmail, headers, restTemplate, kcUpdateUrl);

        try {
            if (keycloakUserDto.getPassword() != null) {

                String dynamicJsonForPassword = String.format(
                        "{\"type\":\"password\",\"temporary\":false,\"value\":\"%s\"}",
                        keycloakUserDto.getPassword());

                restTemplate.exchange(
                        kcUpdateUrl + "/" + userKeycloakId + "/reset-password",
                        HttpMethod.PUT,
                        new HttpEntity<>(dynamicJsonForPassword,
                                headers),
                        Object.class);
            }

            String dynamicJsonForUserInfo = String.format(
                    "{\"firstName\":\"%s\",\"lastName\":\"%s\",\"email\":\"%s\"}",
                    keycloakUserDto.getName(), keycloakUserDto.getSurname(),
                    keycloakUserDto.getEmail());

            restTemplate.exchange(
                    kcUpdateUrl + "/" + userKeycloakId,
                    HttpMethod.PUT,
                    new HttpEntity<>(dynamicJsonForUserInfo,
                            headers),
                    Object.class);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("There is an error when updating..." + e);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body("User updates successfully... Redirecting to sign page...");
    }
}
