package com.vehicle_rental_portal.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vehicle_rental_portal.backend.entity.VehicleEntity;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    public List<VehicleEntity> findByCompanyId(Long id);

    @Query("SELECT DISTINCT v FROM VehicleEntity v " +
            "JOIN v.company c " +
            "JOIN v.availabilityDates ad " +
            "WHERE c.city = :city " +
            "AND v.type = :type " +
            "AND ad BETWEEN :startDate AND :endDate " +
            "AND NOT EXISTS (" +
            "   SELECT r FROM ReservationEntity r " +
            "   WHERE r.vehicle.id = v.id " +
            "   AND (r.startDate BETWEEN :startDate AND :endDate " +
            "   OR r.endDate BETWEEN :startDate AND :endDate " +
            "   OR (r.startDate <= :startDate AND r.endDate >= :endDate))" +
            ")")
    public List<VehicleEntity> findAvailableVehicles(String city, String type, LocalDate startDate, LocalDate endDate);
}
