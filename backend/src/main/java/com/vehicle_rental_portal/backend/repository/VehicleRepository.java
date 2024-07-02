package com.vehicle_rental_portal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vehicle_rental_portal.backend.entity.VehicleEntity;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

}
