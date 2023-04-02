package com.muabatech.jwtauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guest")
public class GuestController {
	//https://www.youtube.com/watch?v=WwtNNbxZsUU&ab_channel=self-code
	@GetMapping("/index")
	public ResponseEntity<String> index(){
		return ResponseEntity.ok("Welcome to my page ! ");
	}
}
