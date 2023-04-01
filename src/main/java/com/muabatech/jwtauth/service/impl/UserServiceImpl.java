package com.muabatech.jwtauth.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.muabatech.jwtauth.dto.UserDTO;
import com.muabatech.jwtauth.entity.Role;
import com.muabatech.jwtauth.entity.User;
import com.muabatech.jwtauth.exception.BaseException;
import com.muabatech.jwtauth.repository.RoleRepository;
import com.muabatech.jwtauth.repository.UserRepository;
import com.muabatech.jwtauth.service.UserService;
import com.muabatech.jwtauth.utils.BaseResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public BaseResponseDTO registerAccount(UserDTO userDTO) {
		BaseResponseDTO response = new BaseResponseDTO();
		validateAccount(userDTO);
		User user = insertUser(userDTO);
		try {
			userRepository.save(user);
			response.setCode(String.valueOf(HttpStatus.OK.value()));
			response.setMessage("Created successfully");
		}catch(Exception e) {
			response.setCode(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
			response.setMessage("Service unavailable");
		}
		return response;
	}
	
	private User insertUser(UserDTO userDTO) {
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		Set<Role> roles = new HashSet<>();
		roles.add(roleRepository.findByName(userDTO.getRole()));
		user.setRoles(roles);
		return user;
	}
	
	private void validateAccount(UserDTO userDTO) {
		//Validate null data
		if(ObjectUtils.isEmpty(userDTO)) {
			throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()),"Data must not be empty");
		}
		
		//Validate duplicate username
		User user = userRepository.findByUsername(userDTO.getUsername());
		if(!ObjectUtils.isEmpty(user)) {
			throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()),"Username already exists");
		}
		
		//Validate role
		List<String> roles = roleRepository.findAll().stream().map(Role::getName).toList();
		if(!roles.contains(userDTO.getRole())) {
			throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()),"Invalid role");
		}
	}

}
