package com.muabatech.jwtauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.muabatech.jwtauth.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;
	private final UserRepository userRepository;
	//private final UserDetailsService userDetailsService;
		@Bean
		public UserDetailsService usersDetailsService() {
			
			return username -> userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
		}
		@Bean
		public PasswordEncoder passwordsEncoder() {
			return new BCryptPasswordEncoder();
		}
		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//			AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
//			builder.userDetailsService(usersDetailsService()).passwordEncoder(passwordsEncoder());
//			AuthenticationManager manager = builder.build();
			http
			.csrf()
			.disable()
			.authorizeHttpRequests()
			.requestMatchers("/api/v1/auth/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
			//http.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
			return http.build();
		}

}
