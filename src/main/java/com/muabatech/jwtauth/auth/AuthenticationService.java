package com.muabatech.jwtauth.auth;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.muabatech.jwtauth.config.JwtService;
import com.muabatech.jwtauth.user.Role;
import com.muabatech.jwtauth.user.User;
import com.muabatech.jwtauth.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	public AuthenticationResponse register(RegisterRequest request) {
		//try {
			var user = User.builder()
					.firstName(request.getFirstName())
					.lastName(request.getLastName())
					.email(request.getEmail())
					.password(passwordEncoder.encode(request.getPassword()))
					.role(Role.USER)
					.build();
			userRepository.save(user);
			var jwtToken = jwtService.generateToken(user);
			//var refreshToken = jwtService.generateRefreshToken(user);
			return AuthenticationResponse.builder()
					.token(jwtToken)
					//.refresh(refreshToken)
					.build();
//		}catch(Exception e) {
//			throw new RequestException(e.getMessage());
//		}
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		//try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getEmail(),
							request.getPassword()));
			var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
			var jwtToken = jwtService.generateToken(user);
			//var refreshToken = jwtService.generateRefreshToken(user);
			return AuthenticationResponse.builder()
					.token(jwtToken)
					//.refresh(refreshToken)
					.build();
//		}catch(Exception e) {
//			throw new RequestException(e.getMessage());
//		}
	}
	public List<User>getAllUsers(){
		return userRepository.findAll();
	}

}
