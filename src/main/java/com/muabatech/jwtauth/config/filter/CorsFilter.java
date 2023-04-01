package com.muabatech.jwtauth.config.filter;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class CorsFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
		httpServletResponse.setHeader("Access-Control-Allow-Methods","*");
		httpServletResponse.setHeader("Access-Control-Allow-Headers","Authorization,Content-Type");
		httpServletResponse.setHeader("Access-Control-Max-Age","3600");
		if(HttpMethod.OPTIONS.name().equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		}
		chain.doFilter(request, response);
	}

}
