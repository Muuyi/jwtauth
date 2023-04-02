package com.muabatech.jwtauth.config.filter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muabatech.jwtauth.dto.LoginRequest;
import com.muabatech.jwtauth.jwt.JwtConfig;
import com.muabatech.jwtauth.jwt.JwtService;
import com.muabatech.jwtauth.service.security.UserDetailsCustom;
import com.muabatech.jwtauth.utils.BaseResponseDTO;
import com.muabatech.jwtauth.utils.HelperUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private final JwtService jwtService;
	private final ObjectMapper objectMapper;
	
	public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager manager, JwtConfig jwtConfig, JwtService jwtService) {
		super(new AntPathRequestMatcher(jwtConfig.getUrl(),"POST"));
		setAuthenticationManager(manager);
		this.objectMapper = new ObjectMapper();
		this.jwtService = jwtService;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response) throws AuthenticationException, IOException,ServletException{
		log.info("Start attempt to authentication");
		LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(),LoginRequest.class);
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword(),Collections.emptyList()));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request,HttpServletResponse response,FilterChain chain,Authentication authentication) throws IOException,ServletException{
		UserDetailsCustom userDetailsCustom = (UserDetailsCustom)authentication.getPrincipal();
		String accessToken = jwtService.generateToken(userDetailsCustom);
		String json = HelperUtils.JSON_WRITER.writeValueAsString(accessToken);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(json);
		//super.successfulAuthentication(request, response, authResult);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,HttpServletResponse response,AuthenticationException failed) throws IOException {
		BaseResponseDTO responseDTO = new BaseResponseDTO();
		responseDTO.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
		responseDTO.setMessage("");
		//responseDTO.setMessage(failed.getLocalMessage());
		String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDTO);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(json);
		//super.unsuccessfulAuthentication(request, response, e);
	}

//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//			throws AuthenticationException, IOException, ServletException {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
