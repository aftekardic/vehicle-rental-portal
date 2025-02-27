package com.vehicle_rental_portal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vehicle_rental_portal.backend.entity.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    public CompanyEntity findByEmail(String email);
}
