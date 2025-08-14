package com.monentreprise.gestionconges.controller;

import com.monentreprise.gestionconges.dto.CreateTypeCongeDTO;
import com.monentreprise.gestionconges.dto.TypeCongeResponseDTO;
import com.monentreprise.gestionconges.dto.UpdateTypeCongeDTO;
import com.monentreprise.gestionconges.entity.TypeConge;
import com.monentreprise.gestionconges.repository.TypeCongeRepository;
import com.monentreprise.gestionconges.service.TypeCongeMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/typeconges")
public class TypeCongeController {

    private final TypeCongeRepository typeCongeRepository;
    private final TypeCongeMapper typeCongeMapper;

    public TypeCongeController(TypeCongeRepository typeCongeRepository, TypeCongeMapper typeCongeMapper) {
        this.typeCongeRepository = typeCongeRepository;
        this.typeCongeMapper = typeCongeMapper;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public ResponseEntity<List<TypeCongeResponseDTO>> getAllTypeConges() {
        List<TypeConge> typeConges = typeCongeRepository.findAll();
        List<TypeCongeResponseDTO> dtos = typeConges.stream()
                .map(typeCongeMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public ResponseEntity<TypeCongeResponseDTO> getTypeCongeById(@PathVariable Long id) {
        return typeCongeRepository.findById(id)
                .map(typeCongeMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<TypeCongeResponseDTO> createTypeConge(@Valid @RequestBody CreateTypeCongeDTO createDTO) {
        TypeConge typeConge = typeCongeMapper.toEntity(createDTO);
        TypeConge saved = typeCongeRepository.save(typeConge);
        return ResponseEntity.status(HttpStatus.CREATED).body(typeCongeMapper.toResponseDTO(saved));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<TypeCongeResponseDTO> updateTypeConge(@PathVariable Long id,
                                                                @Valid @RequestBody UpdateTypeCongeDTO updateDTO) {
        return typeCongeRepository.findById(id)
                .map(typeConge -> {
                    typeCongeMapper.updateEntity(typeConge, updateDTO);
                    TypeConge updated = typeCongeRepository.save(typeConge);
                    return ResponseEntity.ok(typeCongeMapper.toResponseDTO(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTypeConge(@PathVariable Long id) {
        if (!typeCongeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        typeCongeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

