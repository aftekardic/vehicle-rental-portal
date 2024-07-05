package com.vehicle_rental_portal.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vehicle_rental_portal.backend.entity.ReservationEntity;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    public List<ReservationEntity> findByUserId(Long user_id);
}
