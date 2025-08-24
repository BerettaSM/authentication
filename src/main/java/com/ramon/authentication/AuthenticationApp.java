package com.ramon.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ramon.authentication.domain.entities.User;
import com.ramon.authentication.repositories.UserRepository;
import com.ramon.authentication.services.TokenService;

@SpringBootApplication
public class AuthenticationApp implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApp.class, args);
	}

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = userRepository.findById("user@email.com").get();

        System.out.println(user);

        String token = tokenService.generateToken(user);
        System.out.println(token);

        var jwt = tokenService.verify(token);
        System.out.println(jwt.getSubject());
    }

}
