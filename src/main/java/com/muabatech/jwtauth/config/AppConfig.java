package com.muabatech.jwtauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.muabatech.jwtauth.config.filter.CustomAuthenticationProvider;
import com.muabatech.jwtauth.exception.CustomAccessDeniedHandler;
import com.muabatech.jwtauth.service.security.UserDetailsServiceCustom;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class AppConfig {
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceCustom();
	}
	
	@Autowired
	public void configGlobal(final AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(customAuthenticationProvider);
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		builder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
		AuthenticationManager manager = builder.build();
		http
			.cors().disable()
			.csrf().disable()
			.formLogin().disable()
			.authorizeHttpRequests()
			.requestMatchers("/account/**").permitAll()
			.requestMatchers("/guest/**").permitAll()
			.requestMatchers("/admin/**").hasAuthority("ADMIN")
			.requestMatchers("/user/**").hasAuthority("USER")
			.anyRequest().authenticated()
			.and()
			.authenticationManager(manager)
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(
					((request,response,authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
			.accessDeniedHandler(new CustomAccessDeniedHandler())
			.and()
			.addFilterBefore()
			.addFilterAfter();
		return http.build();
	}
}
