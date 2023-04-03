package com.muabatech.jwtauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muabatech.jwtauth.auth.AuthenticationRequest;
import com.muabatech.jwtauth.auth.AuthenticationResponse;
import com.muabatech.jwtauth.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService authenticationService;
	 @PostMapping("/login")
	 public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
		 return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
	 }
}
