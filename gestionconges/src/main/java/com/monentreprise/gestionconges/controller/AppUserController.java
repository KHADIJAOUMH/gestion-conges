package com.monentreprise.gestionconges.controller;


import com.monentreprise.gestionconges.dto.CreateUserDTO;
import com.monentreprise.gestionconges.dto.UpdateUserDTO;
import com.monentreprise.gestionconges.dto.UserResponseDTO;
import com.monentreprise.gestionconges.entity.AppUser;
import com.monentreprise.gestionconges.exception.ResourceNotFoundException;
import com.monentreprise.gestionconges.repository.AppUserRepository;
import com.monentreprise.gestionconges.service.AppUserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AppUserController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppUserMapper appuserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDTO> findAllUsers() {
        return appUserRepository.findAll()
                .stream()
                .map(appuserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(#id, principal.username)")
    public UserResponseDTO findById(@PathVariable Long id) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return appuserMapper.toResponseDTO(user);
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO create(@Valid @RequestBody CreateUserDTO dto) {
        AppUser user = appuserMapper.toEntity(dto);
        AppUser saved = appUserRepository.save(user);
        return appuserMapper.toResponseDTO(saved);
    }

    @PatchMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(#id, principal.username)")
    public UserResponseDTO updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserDTO dto) {
        AppUser existingUser = appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        appuserMapper.updateEntity(existingUser, dto);
        AppUser updated = appUserRepository.save(existingUser);
        return appuserMapper.toResponseDTO(updated);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        if (!appUserRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        appUserRepository.deleteById(id);
    }
    @GetMapping("/users/me")
    @PreAuthorize("isAuthenticated()")
    public UserResponseDTO getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
        return appuserMapper.toResponseDTO(user);
    }

    //Dans AppUserController (ou un @ControllerAdvice global)
    @InitBinder
    public void initBinder(WebDataBinder binder) {binder.registerCustomEditor(String.class, new org.springframework.beans.propertyeditors.StringTrimmerEditor(true));
    }


}
