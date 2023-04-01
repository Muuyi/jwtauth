package com.muabatech.jwtauth.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	@GetMapping("/index")
	public ResponseEntity<String> index(Principal principal){
		return ResponseEntity.ok("Welcome to user page : "+principal.getName());
	}
}
