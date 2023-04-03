package com.muabatech.jwtauth.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.muabatech.jwtauth.entity.Role;
import com.muabatech.jwtauth.entity.User;
import com.muabatech.jwtauth.repository.RoleRepository;
import com.muabatech.jwtauth.repository.UserRepository;
import com.muabatech.jwtauth.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	@Override
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public void addToUser(String username, String rolename) {
		User user = userRepository.findByEmail(username).get();
		Role role = roleRepository.findByName(rolename);
		user.getRoles().add(role);
	}

}
