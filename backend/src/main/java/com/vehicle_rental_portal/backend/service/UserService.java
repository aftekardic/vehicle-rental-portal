package com.vehicle_rental_portal.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vehicle_rental_portal.backend.dto.UserDtos.UserDto;
import com.vehicle_rental_portal.backend.entity.UserEntity;
import com.vehicle_rental_portal.backend.repository.UserRepository;
import com.vehicle_rental_portal.backend.util.ModelConverterUtil;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelConverterUtil modelConverter;

    public ResponseEntity<?> updateUser(String userEmail, UserDto userDto) {
        UserEntity currentUser = userRepository.findByEmail(userEmail);
        currentUser.setEmail(userDto.getEmail());
        currentUser.setName(userDto.getName());
        currentUser.setSurname(userDto.getSurname());
        userRepository.save(currentUser);
        return ResponseEntity.ok().body("User informations was updated.");
    }

    public ResponseEntity<?> getUserInformations(String userEmail) {
        UserDto userDto = modelConverter.userEntityToDto(userRepository.findByEmail(userEmail));
        return ResponseEntity.ok().body(userDto);

    }
}
