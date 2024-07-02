package com.vehicle_rental_portal.backend.entity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.math.BigDecimal;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "vehicles")
public class VehicleEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @Column(name = "type")
    private String type; // binek, arazi, ticari, vb.

    @Column(name = "daily_price")
    private BigDecimal dailyPrice;

    @ElementCollection
    @CollectionTable(name = "price_validity_dates", joinColumns = @JoinColumn(name = "vehicle_id"))
    @Column(name = "date")
    private List<LocalDate> priceValidityDates;

    @ElementCollection
    @CollectionTable(name = "availability_dates", joinColumns = @JoinColumn(name = "vehicle_id"))
    @Column(name = "date")
    private List<LocalDate> availabilityDates;

    @ElementCollection
    @CollectionTable(name = "additional_services", joinColumns = @JoinColumn(name = "vehicle_id"))
    @Column(name = "service")
    private List<String> additionalServices;
}
