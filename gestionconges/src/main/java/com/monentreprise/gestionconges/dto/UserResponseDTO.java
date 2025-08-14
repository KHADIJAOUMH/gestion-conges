package com.monentreprise.gestionconges.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;



@Data
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String cin;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;


}
