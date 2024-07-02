package com.vehicle_rental_portal.backend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "companies")
public class CompanyEntity extends BaseEntity {
    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String city;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    List<VehicleEntity> vehicles;
}
