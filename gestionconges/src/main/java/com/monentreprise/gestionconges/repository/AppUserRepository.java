package com.monentreprise.gestionconges.repository;

import com.monentreprise.gestionconges.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findByUsername(String username);

    //User findByCin(String cin);
   // List<User> findByFirstName(String firstname);
    //List<User> findByLastName(String lastname);
    //List<User> findByFirstNameAndLastName(String firstname,String Lastname);
}
