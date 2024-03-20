package com.paololauria.bnb;

import com.paololauria.bnb.dtos.RegisterRequestDto;
import com.paololauria.bnb.model.repository.abstractions.UserRepository;
import com.paololauria.bnb.services.implementations.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.paololauria.bnb.model.entities.Role.ADMIN;

@SpringBootApplication
public class BnbApplication {

	public static void main(String[] args) {
		SpringApplication.run(BnbApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service,
			UserRepository userRepository
	) {
		return args -> {
			String email = "p@gmail.com";

			if (userRepository.findByEmail(email).isEmpty()) {
				RegisterRequestDto paolo = new RegisterRequestDto(
						"Paolo", "Lauria",
						email, "1234",
						"1996-12-20", ADMIN);

				System.out.println("Token: " + service.register(paolo).getAccessToken());
			} else {
				System.out.println("L'utente con l'email " + email + " esiste già nel database.");
			}
		};
	}
}
