package com.monentreprise.gestionconges.service;

import com.monentreprise.gestionconges.dto.CongeResponseDTO;
import com.monentreprise.gestionconges.dto.CreateCongeDTO;
import com.monentreprise.gestionconges.dto.UpdateCongeDTO;
import com.monentreprise.gestionconges.entity.Conge;
import com.monentreprise.gestionconges.entity.TypeConge;
import com.monentreprise.gestionconges.entity.AppUser;
import com.monentreprise.gestionconges.exception.ResourceNotFoundException;
import com.monentreprise.gestionconges.repository.AppUserRepository;
import com.monentreprise.gestionconges.repository.TypeCongeRepository;
import org.springframework.stereotype.Component;

@Component
public class CongeMapper {

    private final TypeCongeRepository typeCongeRepository;

    public CongeMapper(TypeCongeRepository typeCongeRepository) {
        this.typeCongeRepository = typeCongeRepository;
    }


    public Conge toEntity(CreateCongeDTO dto) {
        Conge conge = new Conge();
        conge.setDateDebut(dto.getDateDebut());
        conge.setDateFin(dto.getDateFin());
        conge.setMotif(dto.getMotif());
        setTypeConge(conge, dto.getTypeCongeId());
        return conge;
    }

    public void updateEntity(Conge conge, UpdateCongeDTO dto) {
        conge.setDateDebut(dto.getDateDebut());
        conge.setDateFin(dto.getDateFin());
        conge.setMotif(dto.getMotif());
        setTypeConge(conge, dto.getTypeCongeId());
    }

    public void setTypeConge(Conge conge, Long typeCongeId) {
        if (typeCongeId != null) {
            TypeConge typeConge = typeCongeRepository.findById(typeCongeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Type de congé non trouvé avec l'id : " + typeCongeId));
            conge.setTypeconge(typeConge);
        }
    }

    public CongeResponseDTO toResponseDTO(Conge conge) {
        CongeResponseDTO dto = new CongeResponseDTO();
        dto.setId(conge.getId());
        dto.setDateDebut(conge.getDateDebut());
        dto.setDateFin(conge.getDateFin());
        dto.setMotif(conge.getMotif());
        dto.setStatus(conge.getStatus() != null ? conge.getStatus().name() : null);

        if (conge.getUser() != null) {
            dto.setUserId(conge.getUser().getId());
            dto.setUsername(conge.getUser().getUsername());
        }

        if (conge.getTypeconge() != null) {
            dto.setTypeCongeId(conge.getTypeconge().getId());
            dto.setTypeCongeName(conge.getTypeconge().getName());
        }

        return dto;
    }
}
