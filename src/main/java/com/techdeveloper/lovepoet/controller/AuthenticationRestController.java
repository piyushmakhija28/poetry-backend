package com.techdeveloper.lovepoet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techdeveloper.lovepoet.annotations.sequence.ValidationSequence;
import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.dto.AuthenticationDto;
import com.techdeveloper.lovepoet.form.LoginForm;
import com.techdeveloper.lovepoet.services.AuthenticationServices;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1")
@AllArgsConstructor
public class AuthenticationRestController {

	private final AuthenticationServices authenticationServices;

	private final RequestAttributeSecurityContextRepository requestAttributeSecurityContextRepository;

	@PostMapping("/login")
	public ResponseEntity<ApiResponseDto<AuthenticationDto>> login(
			@Validated(value = ValidationSequence.class) @RequestBody LoginForm loginForm) {
		ApiResponseDto<AuthenticationDto> apiResponseDto = authenticationServices.login(loginForm.getUsername(),
				loginForm.getPassword());
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@PostMapping("/user/login")
	public ResponseEntity<ApiResponseDto<AuthenticationDto>> userLogin(
			@Validated(value = ValidationSequence.class) @RequestBody LoginForm loginForm) {
		ApiResponseDto<AuthenticationDto> apiResponseDto = authenticationServices.userLogin(loginForm.getUsername(),
				loginForm.getPassword());
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@PutMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest httpServletRequest) {
		SecurityContext securityContext = requestAttributeSecurityContextRepository
				.loadDeferredContext(httpServletRequest).get();
		Long userId = (Long) securityContext.getAuthentication().getPrincipal();
		securityContext.setAuthentication(null);
		SecurityContextHolder.clearContext();
		authenticationServices.logout(userId);
		return ResponseEntity.ok().build();
	}

}
