package com.muabatech.jwtauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muabatech.jwtauth.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class DemoController {
	private final AuthenticationService authenticationService;
	
	@GetMapping("/test")
	public ResponseEntity<String> login(){
		return ResponseEntity.ok("Authentication and authorization is succeeded");
	}
}
