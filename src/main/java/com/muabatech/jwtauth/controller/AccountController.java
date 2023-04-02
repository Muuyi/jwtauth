package com.muabatech.jwtauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muabatech.jwtauth.dto.UserDTO;
import com.muabatech.jwtauth.service.UserService;
import com.muabatech.jwtauth.utils.BaseResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
	private final UserService userService;
	//https://www.youtube.com/watch?v=WwtNNbxZsUU&ab_channel=self-code
	
	@PostMapping("/register")
	public ResponseEntity<BaseResponseDTO> register(@RequestBody UserDTO userDTO){
		return ResponseEntity.ok(userService.registerAccount(userDTO));
	}
}
