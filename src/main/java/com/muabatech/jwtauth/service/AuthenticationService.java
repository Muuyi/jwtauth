package com.muabatech.jwtauth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.muabatech.jwtauth.auth.AuthenticationRequest;
import com.muabatech.jwtauth.auth.AuthenticationResponse;
import com.muabatech.jwtauth.entity.Role;
import com.muabatech.jwtauth.entity.User;
import com.muabatech.jwtauth.repository.RoleCustomRepo;
import com.muabatech.jwtauth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final RoleCustomRepo roleCustomRepo;
	private final JwtService jwtService;
	
	public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
		User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
		List<Role> role = null;
		if(user != null) {
			role = roleCustomRepo.getRole(user);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Set<Role> set = new HashSet<>();
		role.stream().forEach(c -> set.add(new Role(c.getName())));
		user.setRoles(set);
		set.stream().forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getName())));
		var jwtToken = jwtService.generateToken(user, authorities);
		var jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);
		return AuthenticationResponse.builder().token(jwtToken).refreshToken(jwtRefreshToken).build();
	}
}
