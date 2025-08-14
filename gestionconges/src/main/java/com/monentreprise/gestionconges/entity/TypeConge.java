package com.monentreprise.gestionconges.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class TypeConge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "typeconge",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Conge> congeByTyped = new ArrayList<>();
}
