package com.monentreprise.gestionconges.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CreateTypeCongeDTO {
    @NotBlank(message = "Le nom du type de congé est obligatoire")
    private String name;

    private String description;
}
