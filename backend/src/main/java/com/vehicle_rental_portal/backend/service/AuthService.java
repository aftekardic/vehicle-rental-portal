package com.vehicle_rental_portal.backend.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.vehicle_rental_portal.backend.dto.AuthDtos.AuthResponseDto;
import com.vehicle_rental_portal.backend.dto.AuthDtos.LoginRequestDto;
import com.vehicle_rental_portal.backend.dto.AuthDtos.RegisterRequestAsCompanyDto;
import com.vehicle_rental_portal.backend.dto.AuthDtos.RegisterRequestAsUserDto;
import com.vehicle_rental_portal.backend.dto.AuthDtos.TokenDto;
import com.vehicle_rental_portal.backend.dto.CompanyDtos.CompanyDto;
import com.vehicle_rental_portal.backend.dto.UserDtos.UserDto;
import com.vehicle_rental_portal.backend.entity.CompanyEntity;
import com.vehicle_rental_portal.backend.entity.UserEntity;
import com.vehicle_rental_portal.backend.repository.CompanyRepository;
import com.vehicle_rental_portal.backend.repository.UserRepository;
import com.vehicle_rental_portal.backend.util.KeycloakUtil;
import com.vehicle_rental_portal.backend.util.ModelConverterUtil;
import com.vehicle_rental_portal.backend.util.SessionStorageUtil;

@Service
public class AuthService {
    @Autowired
    private KeycloakUtil keycloakUtil;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SessionStorageUtil sessionStorage;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelConverterUtil modelConverter;
    @Value("${keycloak.client-id}")
    private String kcClientId;

    @Value("${keycloak.client-secret}")
    private String kcClientSecret;

    @Value("${keycloak.token-url}")
    private String kcGetTokenUrl;

    @Value("${keycloak.logout-url}")
    private String kcLogoutUrl;

    @Value("${keycloak.revoke-token-url}")
    private String kcRevokeTokenUrl;

    @Value("${keycloak.admin-token-url}")
    private String kcAdminUrl;

    @Value("${keycloak.update-url}")
    private String kcUpdateUrl;

    private static final String GRANT_TYPE_PASSWORD = "password";
    private static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    private static final String ACCESS_TOKEN = "Access-Token";
    private static final String REFRESH_TOKEN = "Refresh-Token";
    private static final String EXPIRES_IN = "Expires-In";

    private static final String DEVICE_ID = "Device-Id";

    public ResponseEntity<?> login(LoginRequestDto loginRequest, HttpServletRequest servletRequest,
            HttpServletResponse servletResponse) {
        String deviceId = servletRequest.getHeader(DEVICE_ID);

        TokenDto tokenDto = keycloakUtil.getAccessToken(loginRequest, restTemplate, GRANT_TYPE_PASSWORD, kcClientId,
                kcClientSecret, kcGetTokenUrl);
        servletResponse.addHeader(ACCESS_TOKEN, tokenDto.getAccess_token());
        servletResponse.addHeader(EXPIRES_IN, String.valueOf(tokenDto.getExpires_in()));
        sessionStorage.putCache(REFRESH_TOKEN, deviceId, tokenDto.getRefresh_token(), 1800);

        return ResponseEntity.ok().body(AuthResponseDto.builder()
                .status("SUCCESS")
                .message("Login successful...")
                .accessToken(tokenDto.getAccess_token())
                .refreshToken(tokenDto.getRefresh_token())
                .build());
    }

    public ResponseEntity<?> registerAsCompany(RegisterRequestAsCompanyDto registerRequest) {
        String adminAccessToken = keycloakUtil.getAdminAccessToken(GRANT_TYPE_PASSWORD, restTemplate, kcAdminUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + adminAccessToken);

        String dynamicJsonForUserInfo = String.format(
                "{\"firstName\":\"%s\",\"email\":\"%s\",\"enabled\":true,\"realmRoles\":[\"COMPANY\"]}",
                registerRequest.getName(), registerRequest.getEmail());
        try {
            ResponseEntity<Object> response = restTemplate.exchange(
                    kcUpdateUrl,
                    HttpMethod.POST,
                    new HttpEntity<>(dynamicJsonForUserInfo,
                            headers),
                    Object.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String newCompanyID = keycloakUtil.getUserSub(registerRequest.getEmail(), headers, restTemplate,
                        kcUpdateUrl);
                String dynamicJsonForPassword = String.format(
                        "{\"type\":\"password\",\"temporary\":false,\"value\":\"%s\"}",
                        registerRequest.getPassword());

                restTemplate.exchange(
                        kcUpdateUrl + "/" + newCompanyID + "/reset-password",
                        HttpMethod.PUT,
                        new HttpEntity<>(dynamicJsonForPassword,
                                headers),
                        Object.class);
            }

            if (companyRepository.findByEmail(registerRequest.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Company already exist");
            } else {

                CompanyDto newCompany = CompanyDto.builder().name(registerRequest.getName())
                        .email(registerRequest.getEmail()).city(registerRequest.getCity()).build();
                CompanyEntity registeredCompanyEntity = modelConverter.companyDtoToEntity(newCompany);
                companyRepository.save(registeredCompanyEntity);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("An error occured when creating this company. Please try again later...");
        }

        return ResponseEntity.ok().body("Company created...");
    }

    public ResponseEntity<?> registerAsUser(RegisterRequestAsUserDto registerRequest) {
        String adminAccessToken = keycloakUtil.getAdminAccessToken(GRANT_TYPE_PASSWORD, restTemplate, kcAdminUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + adminAccessToken);

        String dynamicJsonForUserInfo = String.format(
                "{\"firstName\":\"%s\",\"lastName\":\"%s\",\"email\":\"%s\",\"enabled\":true,\"realmRoles\":[\"COMPANY\"]}",
                registerRequest.getName(), registerRequest.getSurname(), registerRequest.getEmail());
        try {
            ResponseEntity<Object> response = restTemplate.exchange(
                    kcUpdateUrl,
                    HttpMethod.POST,
                    new HttpEntity<>(dynamicJsonForUserInfo,
                            headers),
                    Object.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String newUserID = keycloakUtil.getUserSub(registerRequest.getEmail(), headers, restTemplate,
                        kcUpdateUrl);
                String dynamicJsonForPassword = String.format(
                        "{\"type\":\"password\",\"temporary\":false,\"value\":\"%s\"}",
                        registerRequest.getPassword());

                restTemplate.exchange(
                        kcUpdateUrl + "/" + newUserID + "/reset-password",
                        HttpMethod.PUT,
                        new HttpEntity<>(dynamicJsonForPassword,
                                headers),
                        Object.class);
            }

            if (companyRepository.findByEmail(registerRequest.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exist");
            } else {

                UserDto newUser = UserDto.builder().name(registerRequest.getName())
                        .email(registerRequest.getEmail()).surname(registerRequest.getSurname()).build();
                UserEntity registeredUserEntity = modelConverter.userDtoToEntity(newUser);
                userRepository.save(registeredUserEntity);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("An error occured when creating this user. Please try again later...");
        }

        return ResponseEntity.ok().body("User created...");
    }

    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletRequest servletRequest) {
        String deviceId = servletRequest.getHeader(DEVICE_ID);
        String refreshToken = (String) sessionStorage.getCache(REFRESH_TOKEN, deviceId);
        if (refreshToken == null) {
            return ResponseEntity.badRequest().body("Refresh-Token is missing or invalid");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", kcClientId);
        requestBody.add("client_secret", kcClientSecret);
        requestBody.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> logoutResponse = restTemplate.postForEntity(kcLogoutUrl, entity, String.class);

        requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", kcClientId);
        requestBody.add("client_secret", kcClientSecret);
        requestBody.add("token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> revokeEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> revokeResponse = restTemplate.postForEntity(kcRevokeTokenUrl, revokeEntity,
                String.class);

        if (logoutResponse.getStatusCode().is2xxSuccessful() && revokeResponse.getStatusCode().is2xxSuccessful()) {
            sessionStorage.removeCache(REFRESH_TOKEN, deviceId);
            return ResponseEntity.ok().body("Logout successful...");
        } else {
            return ResponseEntity.status(logoutResponse.getStatusCode()).body("Logout failed...");
        }
    }

    public ResponseEntity<Object> refreshToken(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        String deviceId = servletRequest.getHeader(DEVICE_ID);
        String refreshToken = (String) sessionStorage.getCache(REFRESH_TOKEN, deviceId);

        TokenDto tokenDto = keycloakUtil.getRefreshToken(refreshToken, restTemplate, GRANT_TYPE_REFRESH_TOKEN,
                kcClientId, kcClientSecret, kcGetTokenUrl);

        servletResponse.addHeader(ACCESS_TOKEN, tokenDto.getAccess_token());
        servletResponse.addHeader(EXPIRES_IN, String.valueOf(tokenDto.getExpires_in()));

        sessionStorage.putCache(REFRESH_TOKEN, deviceId, tokenDto.getRefresh_token(), tokenDto.getRefresh_expires_in());

        return ResponseEntity.ok().body(AuthResponseDto.builder()
                .status("SUCCESS")
                .message("Token was refreshed...")
                .accessToken(tokenDto.getAccess_token())
                .refreshToken(tokenDto.getRefresh_token())
                .build());
    }
}
