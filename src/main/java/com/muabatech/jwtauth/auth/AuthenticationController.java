package com.muabatech.jwtauth.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService authService;
	//private final UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
		return ResponseEntity.created(null).body(authService.register(request));
		//return ResponseEntity.ok(authService.register(request));
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
		return ResponseEntity.ok(authService.authenticate(request));
	}
//	@PostMapping("/refresh")
//	public ResponseEntity<RefreshResponse> refresh(@RequestBody RefreshPayload request){
////		ServerResponse serverResponse = new ServerResponse();
////		serverResponse.setCode(201);
////		serverResponse.setMessage("Success");
////		serverResponse.setObject(authService.refresh(request));
//		return new ResponseEntity<RefreshResponse>(authService.refresh(request),HttpStatus.OK);
//	}
}
