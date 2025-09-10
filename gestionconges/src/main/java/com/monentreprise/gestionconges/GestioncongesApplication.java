package com.monentreprise.gestionconges;

import com.monentreprise.gestionconges.entity.AppUser;
import com.monentreprise.gestionconges.entity.Role;
import com.monentreprise.gestionconges.repository.AppUserRepository;
import com.monentreprise.gestionconges.repository.CongeRepository;
import com.monentreprise.gestionconges.repository.TypeCongeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;

@SpringBootApplication
public class GestioncongesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestioncongesApplication.class, args);
	}


	@Bean
	public CommandLineRunner start(AppUserRepository userRepository,
								   CongeRepository congeRepository,
								   PasswordEncoder passwordEncoder, TypeCongeRepository typeCongeRepository) {
		return args -> {


			AppUser user1 = new AppUser();
			user1.setUsername("khadija3");
			user1.setPassword(passwordEncoder.encode("123")); // Encodage BCrypt
			user1.setFirstName("khadija");
			user1.setEmail("user1@example.com");
			user1.setDateOfBirth(LocalDate.of(2007, Month.AUGUST,14));
			user1.setPhone("0654238945");
			user1.getRoles().add(Role.ADMIN); // Enum


			AppUser user2 = new AppUser();
			user2.setUsername("amina3");
			user2.setPassword(passwordEncoder.encode("12345")); // Encodage BCrypt
			user2.setFirstName("amina");
			user2.setDateOfBirth(LocalDate.of(2002, Month.AUGUST,14));
			user2.setPhone("0678766354");
			user2.setEmail("user2@example.com");
			user2.getRoles().add(Role.EMPLOYEE); // Enum


			AppUser user3 = new AppUser();
			user3.setUsername("hana3");
			user3.setPassword(passwordEncoder.encode("123456")); // Encodage BCrypt
			user3.setFirstName("hanoo");
			user3.setEmail("user3@example.com");
			user3.setDateOfBirth(LocalDate.of(2003, Month.AUGUST,14));
			user3.setPhone("0678766354");
			user3.getRoles().add(Role.EMPLOYEE); // Enum



			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);


			userRepository.findAll().forEach(System.out::println);
		};
	}

}
