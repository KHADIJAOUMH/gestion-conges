package com.monentreprise.gestionconges.repository;

import com.monentreprise.gestionconges.entity.Conge;
import com.monentreprise.gestionconges.entity.StatutConge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CongeRepository extends JpaRepository<Conge,Long> {
    public void deleteByUserId(long userId);
    List<Conge> findByUserId(Long userId);
    List<Conge> findByStatus(StatutConge status);




}
