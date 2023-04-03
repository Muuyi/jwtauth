package com.muabatech.jwtauth;

import java.util.HashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.muabatech.jwtauth.entity.Role;
import com.muabatech.jwtauth.entity.User;
import com.muabatech.jwtauth.service.UserService;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories
public class JwtauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtauthApplication.class, args);
	}
	
	@Bean
	BCryptPasswordEncoder brBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	CommandLineRunner run(UserService userService) {
//		return args -> {
//			userService.saveRole(new Role(null,"ROLE_USER"));
//			userService.saveRole(new Role(null,"ROLE_MANAGER"));
//			userService.saveRole(new Role(null,"ROLE_ADMIN"));
//			userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));
//			
//			userService.saveUser(new User(null,"Long Thanh","LongKame","thanhlong@gmail.com","123456",new HashSet<>()));
//			userService.saveUser(new User(null,"Nam Thanh","NamNunez","thanhnam@gmail.com","123456",new HashSet<>()));
//			
//			userService.addToUser("thanhlong@gmail.com", "ROLE_USER");
//			userService.addToUser("thanhlong@gmail.com", "ROLE_MANAGER");
//			
//			userService.addToUser("thanhnam@gmail.com", "ROLE_ADMIN");
//			userService.addToUser("thanhnam@gmail.com", "ROLE_SUPER_ADMIN");
//		};
//	}

}
