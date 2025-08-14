package com.monentreprise.gestionconges.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class Conge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String motif;

    @Enumerated(EnumType.STRING)
    private StatutConge status;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user ;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "typeconge_id")
    private TypeConge typeconge;


}
