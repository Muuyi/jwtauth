package com.muabatech.jwtauth.jwt;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Data
public class JwtConfig {
	@Value("${jwt.url:/jwt/login}")
	private String url;
	@Value("${jwt.header:Authorization}")
	private String header;
	@Value("${jwt.prefix:Bearer}")
	private String prefix;
	@Value("${jwt.secret:4E635266556A586E3272357538782F413F4428472B4B6250655367566B597033}")
	private String secret;
	@Value("${jwt.expiration:#{60*60}}")
	private int expiration;
}
