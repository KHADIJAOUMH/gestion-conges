package com.monentreprise.gestionconges.dto;

import com.monentreprise.gestionconges.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateUserDTO {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String cin;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private List<Role> roles;
}
