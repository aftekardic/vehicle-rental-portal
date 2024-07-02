package com.vehicle_rental_portal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vehicle_rental_portal.backend.entity.ReservationEntity;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

}
