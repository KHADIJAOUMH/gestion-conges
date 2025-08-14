package com.monentreprise.gestionconges.service;

import com.monentreprise.gestionconges.dto.CreateUserDTO;
import com.monentreprise.gestionconges.dto.UpdateUserDTO;
import com.monentreprise.gestionconges.dto.UserResponseDTO;
import com.monentreprise.gestionconges.entity.AppUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



@Component
public class AppUserMapper {

    private final PasswordEncoder passwordEncoder;

    public AppUserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser toEntity(CreateUserDTO dto) {
        AppUser user = new AppUser();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setCin(dto.getCin());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setRoles(dto.getRoles());
        return user;
    }

    public void updateEntity(AppUser user, UpdateUserDTO dto) {
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setCin(dto.getCin());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setRoles(dto.getRoles());
    }

    public UserResponseDTO toResponseDTO(AppUser user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getCin(),
                user.getPhone(),
                user.getAddress(),
                user.getDateOfBirth()
        );
    }
}
