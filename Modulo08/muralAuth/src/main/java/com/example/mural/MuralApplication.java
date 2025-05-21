package com.example.mural;

import com.example.mural.repositories.User;
import com.example.mural.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MuralApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(MuralApplication.class, args);

		var userRepository = context.getBean(UserRepository.class);
		if (userRepository.count() == 0) {	// sem usuários, adiciona alguns
			userRepository.save("admin", "admin", "ADMIN");
			userRepository.save("user", "user", "USER");
		}
	}
}
