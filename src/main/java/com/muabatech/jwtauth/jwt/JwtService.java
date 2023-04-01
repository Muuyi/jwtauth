package com.muabatech.jwtauth.jwt;

import java.security.Key;

import com.muabatech.jwtauth.service.security.UserDetailsCustom;

import io.jsonwebtoken.Claims;

public interface JwtService {
	Claims extractClaims(String token);
	Key getKey();
	String generateToken(UserDetailsCustom userDetailsCustom);
	boolean isValidToken(String token);
}
