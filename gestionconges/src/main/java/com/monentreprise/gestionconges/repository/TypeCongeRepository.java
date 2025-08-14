package com.monentreprise.gestionconges.repository;

import com.monentreprise.gestionconges.entity.TypeConge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeCongeRepository extends JpaRepository<TypeConge,Long> {
}
