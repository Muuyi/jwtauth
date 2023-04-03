package com.muabatech.jwtauth.demo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muabatech.jwtauth.auth.AuthenticationResponse;
import com.muabatech.jwtauth.auth.AuthenticationService;
import com.muabatech.jwtauth.auth.RegisterRequest;
import com.muabatech.jwtauth.user.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/demo-controller")
@RequiredArgsConstructor
public class DemoController {
	private final AuthenticationService authService;
	@GetMapping
	public ResponseEntity<String> sayHello(){
		return ResponseEntity.ok("Hello from secured endpoint");
	}
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers(){
		return ResponseEntity.ok(authService.getAllUsers());
	}

}
