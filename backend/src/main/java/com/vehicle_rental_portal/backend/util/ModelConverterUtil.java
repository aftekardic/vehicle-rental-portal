package com.vehicle_rental_portal.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehicle_rental_portal.backend.dto.CompanyDtos.CompanyDto;
import com.vehicle_rental_portal.backend.dto.UserDtos.UserDto;
import com.vehicle_rental_portal.backend.dto.VehicleDtos.VehicleDto;
import com.vehicle_rental_portal.backend.dto.VehicleDtos.VehicleResponseDto;
import com.vehicle_rental_portal.backend.entity.CompanyEntity;
import com.vehicle_rental_portal.backend.entity.UserEntity;
import com.vehicle_rental_portal.backend.entity.VehicleEntity;
import com.vehicle_rental_portal.backend.repository.CompanyRepository;

@Service
public class ModelConverterUtil {
    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity companyDtoToEntity(CompanyDto companyDto) {
        if (companyDto == null) {
            return null;
        }

        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setName(companyDto.getName());
        companyEntity.setCity(companyDto.getCity());
        companyEntity.setEmail(companyDto.getEmail());
        return companyEntity;
    }

    public CompanyDto companyEntityToDto(CompanyEntity companyEntity) {
        if (companyEntity == null) {
            return null;
        }

        return CompanyDto.builder()
                .name(companyEntity.getName())
                .city(companyEntity.getCity())
                .email(companyEntity.getEmail())
                .build();
    }

    public UserEntity userDtoToEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDto.getName());
        userEntity.setSurname(userDto.getSurname());
        userEntity.setEmail(userDto.getEmail());
        return userEntity;
    }

    public UserDto userEntityToDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return UserDto.builder()
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .email(userEntity.getEmail())
                .build();
    }

    public VehicleEntity vehicleDtoToEntity(VehicleDto vehicleDto) {
        if (vehicleDto == null) {
            return null;
        }

        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setType(vehicleDto.getType());
        vehicleEntity.setDailyPrice(vehicleDto.getDailyPrice());
        vehicleEntity.setAvailabilityDates(vehicleDto.getAvailabilityDates());
        vehicleEntity.setAdditionalServices(vehicleDto.getAdditionalServices());

        // Assuming you have a method to fetch company by email from repository
        CompanyEntity company = companyRepository.findByEmail(vehicleDto.getCompanyEmail());
        if (company == null) {
            return null;
        }

        vehicleEntity.setCompany(company);

        return vehicleEntity;
    }

    public VehicleDto vehicleEntityToDto(VehicleEntity vehicleEntity) {
        if (vehicleEntity == null) {
            return null;
        }

        return VehicleDto.builder()
                .type(vehicleEntity.getType())
                .dailyPrice(vehicleEntity.getDailyPrice())
                .availabilityDates(vehicleEntity.getAvailabilityDates())
                .additionalServices(vehicleEntity.getAdditionalServices())
                .companyEmail(vehicleEntity.getCompany().getEmail())
                .build();
    }

    public VehicleResponseDto vehicleEntityToResponseDto(VehicleEntity vehicleEntity) {
        if (vehicleEntity == null) {
            return null;
        }
        return VehicleResponseDto.builder()
                .type(vehicleEntity.getType())
                .dailyPrice(vehicleEntity.getDailyPrice())
                .availabilityDates(vehicleEntity.getAvailabilityDates())
                .additionalServices(vehicleEntity.getAdditionalServices())
                .companyId(vehicleEntity.getCompany().getId())
                .id(vehicleEntity.getId())
                .build();
    }
}
