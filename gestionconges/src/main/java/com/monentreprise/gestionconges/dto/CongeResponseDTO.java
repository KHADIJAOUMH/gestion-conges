package com.monentreprise.gestionconges.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @AllArgsConstructor @NoArgsConstructor
public class CongeResponseDTO {
    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String motif;
    private String status;

    private Long userId;
    private String username;

    private Long typeCongeId;
    private String typeCongeName;
}
