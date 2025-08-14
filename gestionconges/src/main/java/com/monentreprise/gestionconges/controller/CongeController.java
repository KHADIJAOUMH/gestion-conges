package com.monentreprise.gestionconges.controller;

import com.monentreprise.gestionconges.dto.CongeResponseDTO;
import com.monentreprise.gestionconges.dto.CreateCongeDTO;
import com.monentreprise.gestionconges.dto.UpdateCongeDTO;
import com.monentreprise.gestionconges.entity.Conge;
import com.monentreprise.gestionconges.entity.StatutConge;
import com.monentreprise.gestionconges.entity.AppUser;
import com.monentreprise.gestionconges.exception.ResourceNotFoundException;
import com.monentreprise.gestionconges.repository.CongeRepository;
import com.monentreprise.gestionconges.repository.AppUserRepository;
import com.monentreprise.gestionconges.service.CongeMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CongeController {

    private final CongeRepository congeRepository;
    private final AppUserRepository appUserRepository;
    private final CongeMapper congeMapper;

    public CongeController(CongeRepository congeRepository,
                           AppUserRepository appUserRepository,
                           CongeMapper congeMapper) {
        this.congeRepository = congeRepository;
        this.appUserRepository = appUserRepository;
        this.congeMapper = congeMapper;
    }

    @PostMapping("/conges")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<CongeResponseDTO> createConge(
            @Valid @RequestBody CreateCongeDTO createCongeDTO,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {

        AppUser user = appUserRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        Conge conge = congeMapper.toEntity(createCongeDTO);
        conge.setUser(user);
        conge.setStatus(StatutConge.PENDING);
        congeMapper.setTypeConge(conge, createCongeDTO.getTypeCongeId());

        Conge savedConge = congeRepository.save(conge);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(congeMapper.toResponseDTO(savedConge));
    }

    @GetMapping("/conges")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<CongeResponseDTO>> getAllConges() {
        List<Conge> conges = congeRepository.findAll();
        List<CongeResponseDTO> dtos = conges.stream()
                .map(congeMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/conges/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER') or @securityService.isCongeOwner(#id, principal.username)")
    public ResponseEntity<CongeResponseDTO> getCongeById(@PathVariable Long id) {
        Conge conge = congeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Congé non trouvé avec l'id : " + id));
        return ResponseEntity.ok(congeMapper.toResponseDTO(conge));
    }

    @GetMapping("/conges/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER') or @securityService.isUser(#userId, principal.username)")
    public ResponseEntity<List<CongeResponseDTO>> getCongesByUser(@PathVariable Long userId) {
        List<Conge> conges = congeRepository.findByUserId(userId);
        List<CongeResponseDTO> dtos = conges.stream()
                .map(congeMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/conges/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<CongeResponseDTO>> getCongesByStatus(@PathVariable StatutConge status) {
        List<Conge> conges = congeRepository.findByStatus(status);
        List<CongeResponseDTO> dtos = conges.stream()
                .map(congeMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/conges/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isCongeOwner(#id, principal.username)")
    public ResponseEntity<CongeResponseDTO> updateConge(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCongeDTO updateCongeDTO) {

        Conge conge = congeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Congé non trouvé avec l'id : " + id));

        congeMapper.updateEntity(conge, updateCongeDTO);

        Conge updated = congeRepository.save(conge);
        return ResponseEntity.ok(congeMapper.toResponseDTO(updated));
    }

    @DeleteMapping("/conges/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isCongeOwner(#id, principal.username)")
    public ResponseEntity<Void> deleteConge(@PathVariable Long id) {
        Conge conge = congeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Congé non trouvé avec l'id : " + id));
        congeRepository.delete(conge);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/conges/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCongeByUser(@PathVariable Long userId) {
        congeRepository.deleteByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/conges/{id}/approve")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<CongeResponseDTO> approveConge(@PathVariable Long id) {
        Conge conge = congeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Congé non trouvé avec l'id : " + id));
        conge.setStatus(StatutConge.APPROVED);
        Conge updated = congeRepository.save(conge);
        return ResponseEntity.ok(congeMapper.toResponseDTO(updated));
    }

    @PatchMapping("/conges/{id}/reject")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<CongeResponseDTO> rejectConge(@PathVariable Long id) {
        Conge conge = congeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Congé non trouvé avec l'id : " + id));
        conge.setStatus(StatutConge.REJECTED);
        Conge updated = congeRepository.save(conge);
        return ResponseEntity.ok(congeMapper.toResponseDTO(updated));
    }
}


