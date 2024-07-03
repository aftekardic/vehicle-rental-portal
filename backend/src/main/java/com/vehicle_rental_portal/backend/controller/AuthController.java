package com.vehicle_rental_portal.backend.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle_rental_portal.backend.dto.AuthDtos.LoginRequestDto;
import com.vehicle_rental_portal.backend.dto.AuthDtos.RegisterRequestAsCompanyDto;
import com.vehicle_rental_portal.backend.dto.AuthDtos.RegisterRequestAsUserDto;
import com.vehicle_rental_portal.backend.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/sign-in")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest, HttpServletRequest servletRequest,
            HttpServletResponse servletResponse) {
        return authService.login(loginRequest, servletRequest, servletResponse);
    }

    @PostMapping("/register-company")
    public ResponseEntity<?> registerAsCompany(@RequestBody RegisterRequestAsCompanyDto registerRequest) {
        return authService.registerAsCompany(registerRequest);
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> registerAsUser(@RequestBody RegisterRequestAsUserDto registerRequest) {
        return authService.registerAsUser(registerRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletRequest servletRequest) {
        return authService.logout(request, servletRequest);
    }

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        return authService.refreshToken(servletRequest, servletResponse);
    }
}
