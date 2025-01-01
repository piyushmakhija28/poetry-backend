package com.techdeveloper.lovepoet.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public class AuthenticationFilter extends org.springframework.security.web.authentication.AuthenticationFilter{
	
	public AuthenticationFilter(AuthenticationManager authenticationManager,
			AuthenticationConverter authenticationConverter,HandlerExceptionResolver handlerExceptionResolver) {
		super(authenticationManager, authenticationConverter);
		setFailureHandler(new AuthenticationFailureHandler(handlerExceptionResolver));
		setSuccessHandler(new AuthenticationSuccessHandler());
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getRequestURL().toString().contains("/api/v1/login") || request.getRequestURL().toString().contains("/api/v1/user/login") || request.getRequestURL().toString().contains("/api/v1/register")
				|| (request.getRequestURL().toString().contains("/api/v1/poetries") && request.getMethod().equals(HttpMethod.GET.name())) || request.getRequestURL().toString().contains("/api/v1/poetries/{id}") || request.getRequestURL().toString().contains("/api/v1/poetries/category/{category}");
	}

}
