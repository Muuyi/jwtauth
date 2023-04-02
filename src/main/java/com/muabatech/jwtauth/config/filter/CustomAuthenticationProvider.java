package com.muabatech.jwtauth.config.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.muabatech.jwtauth.entity.Role;
import com.muabatech.jwtauth.entity.User;
import com.muabatech.jwtauth.exception.BaseException;
import com.muabatech.jwtauth.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider{
	@Autowired
	private UserRepository userRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final String username = authentication.getName();
		final String password = authentication.getCredentials().toString();
		User user;
		try {
			user = userRepository.findByUsername(username);
		}catch(Exception e) {
			throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()),"User is not found");
		}
		final List<GrantedAuthority> authorities = getAuthorities(user.getRoles().stream().toList());
		final Authentication auth = new UsernamePasswordAuthenticationToken(username,password,authorities);
		log.info("End actual authentication");
		return auth;
	}
	private List<GrantedAuthority> getAuthorities(List<Role> roles){
		List<GrantedAuthority> result = new ArrayList<>();
		Set<String> permissions = new HashSet<>();
		if(!ObjectUtils.isEmpty(roles)) {
			roles.forEach(r -> {
				permissions.add(r.getName());
			});
		}
		permissions.forEach(p -> {
			result.add(new SimpleGrantedAuthority(p));
		});
		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}

}
