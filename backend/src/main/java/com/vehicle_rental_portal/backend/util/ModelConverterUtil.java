package com.vehicle_rental_portal.backend.util;

import org.springframework.stereotype.Service;

import com.vehicle_rental_portal.backend.dto.CompanyDtos.CompanyDto;
import com.vehicle_rental_portal.backend.dto.UserDtos.UserDto;
import com.vehicle_rental_portal.backend.entity.CompanyEntity;
import com.vehicle_rental_portal.backend.entity.UserEntity;

@Service
public class ModelConverterUtil {

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
}
