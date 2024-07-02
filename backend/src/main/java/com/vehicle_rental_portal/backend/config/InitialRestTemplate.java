package com.vehicle_rental_portal.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class InitialRestTemplate {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
