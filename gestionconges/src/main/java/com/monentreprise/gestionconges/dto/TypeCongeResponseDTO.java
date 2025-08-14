package com.monentreprise.gestionconges.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TypeCongeResponseDTO {

    private Long id;
    private String name;
    private String description;
}
