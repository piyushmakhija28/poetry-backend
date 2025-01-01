package com.techdeveloper.lovepoet.config;

import java.time.Instant;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.util.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.techdeveloper.lovepoet.entity.User;
import com.techdeveloper.lovepoet.repository.UserAuthoritiesRepository;
import com.techdeveloper.lovepoet.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class AuthenticationConvertor implements AuthenticationConverter {

	private final RequestAttributeSecurityContextRepository requestAttributeSecurityContextRepository;

	private final UserRepository userRepository;
	
	private final UserAuthoritiesRepository userAuthoritiesRepository;
	
	private final HttpServletResponse httpServletResponse;
	
	@Override
	public Authentication convert(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (StringUtils.hasLength(header)) {
			String bearerToken = header.split("Bearer ")[1];
			log.info("JWT Token :: {} " + bearerToken);
			try {
				DecodedJWT decodedJWT = JWT.decode(bearerToken);
				int expiryValue = decodedJWT.getExpiresAtAsInstant().compareTo(Instant.now());
				if (expiryValue == -1) {
					throw new CredentialsExpiredException("Token is being expired try with new token.");
				}
				User user= userRepository.findByEmail(decodedJWT.getSubject());
				com.techdeveloper.lovepoet.config.Authentication authentication=new com.techdeveloper.lovepoet.config.Authentication(user, userAuthoritiesRepository, true);
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				securityContext.setAuthentication(authentication);
				requestAttributeSecurityContextRepository.saveContext(securityContext, request, httpServletResponse);
				return authentication;
			} catch (JWTDecodeException jwtDecodeException) {
				throw new BadCredentialsException(jwtDecodeException.getMessage());
			}
		}
		throw new InsufficientAuthenticationException("Token ni mila");
	}

}
