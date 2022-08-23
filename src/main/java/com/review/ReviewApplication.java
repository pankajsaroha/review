package com.review;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.review.model.Role;
import com.review.repository.RoleRepository;


@SpringBootApplication
public class ReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(RoleRepository roleRepository) {

	    return args -> {

	        com.review.model.Role adminRole = roleRepository.findByRole("ADMIN");
	        if (adminRole == null) {
	            Role newAdminRole = new Role();
	            newAdminRole.setRole("ADMIN");
	            roleRepository.save(newAdminRole);
	        }

	        Role userRole = roleRepository.findByRole("USER");
	        if (userRole == null) {
	            Role newUserRole = new Role();
	            newUserRole.setRole("USER");
	            roleRepository.save(newUserRole);
	        }
	    };

	}

}
