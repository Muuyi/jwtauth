package com.muabatech.jwtauth.jwt.impl;

import java.security.Key;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.muabatech.jwtauth.exception.BaseException;
import com.muabatech.jwtauth.jwt.JwtConfig;
import com.muabatech.jwtauth.jwt.JwtService;
import com.muabatech.jwtauth.service.security.UserDetailsCustom;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {
	//private final JwtConfig jwtConfig;
	private static final String SECRET_KEY = "4E635266556A586E3272357538782F413F4428472B4B6250655367566B597033";
	private final UserDetailsService userDetailsService;
	@Override
	public Claims extractClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	@Override
	public Key getKey() {
		//jwtConfig.getSecret()
		byte[] key = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(key);
	}

	@Override
	public String generateToken(UserDetailsCustom userDetailsCustom) {
		//jwtConfig.getExpiration()
		long tm = 1000 * 60 * 30;
		Instant now = Instant.now();
		List<String> roles = new ArrayList<>();
		userDetailsCustom.getAuthorities().forEach(role -> {
			roles.add(role.getAuthority());
		});
		log.info("Roles: {}", roles);
		return Jwts.builder()
				.setSubject(userDetailsCustom.getUsername())
				.claim("authorities", userDetailsCustom.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.claim("roles", roles)
				.claim("isEnable", userDetailsCustom.isEnabled())
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plusSeconds(tm)))
				.signWith(getKey(),SignatureAlgorithm.HS256)
				.compact();
	}

	@Override
	public boolean isValidToken(String token) {
		final String username = extractUsername(token);
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return !ObjectUtils.isEmpty(userDetails);
	}
	
	private String extractUsername(String token) {
		return extractClaims(token,Claims::getSubject);
	}
	
	private <T> T extractClaims(String token, Function<Claims,T>claimsTFunction) {
		final Claims claims = extractClaims(token);
		return claimsTFunction.apply(claims);
	}
	private Claims extractAllClaims(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parserBuilder()
					.setSigningKey(getKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
//		}catch(ExpiredJwtException e) {
//			throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()),"Token expiration");
//		}catch(UnsupportedJwtException e) {
//			throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()),"Token's not supported");
//		}catch(MalformedJwtException e) {
//			throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()),"Invalid format 3 part of token");
//		}catch(SignatureException e) {
//			throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()),"Invalid formart token");
		}catch(Exception e) {
			throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()),e.getLocalizedMessage());
		}
		return claims;
	}

}
