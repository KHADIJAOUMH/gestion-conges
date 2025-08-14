package com.monentreprise.gestionconges.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateCongeDTO {

    @NotNull(message = "La date de début est obligatoire")
    @FutureOrPresent(message = "La date de début doit être aujourd'hui ou dans le futur")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    @FutureOrPresent(message = "La date de fin doit être aujourd'hui ou dans le futur")
    private LocalDate dateFin;

    @NotBlank(message = "Le motif est obligatoire")
    private String motif;

    @NotNull(message = "Le type de congé est obligatoire")
    private Long typeCongeId;

    private Long userId;

    private String status;
}
