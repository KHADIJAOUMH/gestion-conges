package com.monentreprise.gestionconges.dto;

import com.monentreprise.gestionconges.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class UpdateUserDTO {
    private String username;

    @Email(message = "Email invalide")
    private String email;

    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;

    private String firstName;

    private String lastName;

    private String cin;

    @Pattern(regexp = "^[0-9]{10}$", message = "Le numéro de téléphone doit contenir 10 chiffres")
    private String phone;

    private String address;

    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateOfBirth;

    private List<Role> roles;
}
