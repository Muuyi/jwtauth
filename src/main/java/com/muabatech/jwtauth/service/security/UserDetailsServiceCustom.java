package com.muabatech.jwtauth.service.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

import com.muabatech.jwtauth.entity.User;
import com.muabatech.jwtauth.exception.BaseException;
import com.muabatech.jwtauth.repository.UserRepository;

public class UserDetailsServiceCustom implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetailsCustom userDetailsCustom = getUserDetails(username);
		if(ObjectUtils.isEmpty(userDetailsCustom)) {
			throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Invalid username or password");
		}
		return userDetailsCustom;
	}
	
	private UserDetailsCustom getUserDetails(String username) {
		User user = userRepository.findByUsername(username);
		if(ObjectUtils.isEmpty(user)) {
			throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Invalid username or password");
		}
		return new UserDetailsCustom(
				user.getUsername(),
				user.getPassword(),
				user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList())
		);		
				
	}

}
