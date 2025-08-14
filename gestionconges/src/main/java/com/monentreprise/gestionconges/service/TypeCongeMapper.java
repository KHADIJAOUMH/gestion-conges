package com.monentreprise.gestionconges.service;

import com.monentreprise.gestionconges.dto.CreateTypeCongeDTO;
import com.monentreprise.gestionconges.dto.TypeCongeResponseDTO;
import com.monentreprise.gestionconges.dto.UpdateTypeCongeDTO;
import com.monentreprise.gestionconges.entity.TypeConge;
import org.springframework.stereotype.Component;

@Component
public class TypeCongeMapper {

    public TypeConge toEntity(CreateTypeCongeDTO dto) {
        if (dto == null) return null;

        TypeConge typeConge = new TypeConge();
        typeConge.setName(dto.getName());
        typeConge.setDescription(dto.getDescription());
        return typeConge;
    }

    public void updateEntity(TypeConge typeConge, UpdateTypeCongeDTO dto) {
        typeConge.setName(dto.getName());
        typeConge.setDescription(dto.getDescription());
    }

    public TypeCongeResponseDTO toResponseDTO(TypeConge typeConge) {
        TypeCongeResponseDTO dto = new TypeCongeResponseDTO();
        dto.setId(typeConge.getId());
        dto.setName(typeConge.getName());
        dto.setDescription(typeConge.getDescription());
        return dto;
    }
}
