package com.upb.deskBooking;

import com.upb.deskBooking.repository.RoleRepository;
import com.upb.deskBooking.repository.model.ERole;
import com.upb.deskBooking.repository.model.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DeskBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeskBookingApplication.class, args);
	}

	@Bean
	CommandLineRunner run (RoleRepository repository) {
		return args -> {
			if (!repository.findByName(ERole.ROLE_USER).isPresent())
				repository.save(new Role(ERole.ROLE_USER));

			if (!repository.findByName(ERole.ROLE_ADMIN).isPresent())
				repository.save(new Role(ERole.ROLE_ADMIN));

			if (!repository.findByName(ERole.ROLE_MODERATOR).isPresent())
				repository.save(new Role(ERole.ROLE_MODERATOR));
		};
	}
}
