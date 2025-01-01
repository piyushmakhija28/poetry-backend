package com.techdeveloper.lovepoet.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.techdeveloper.lovepoet.dto.ApiResponseDto;

@RestControllerAdvice
public class GloablExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = InsufficientAuthenticationException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleInsufficientAuthenticationException(
			InsufficientAuthenticationException insufficientAuthenticationException) {
		ApiResponseDto<Void> apiResponseDto = ApiResponseDto.<Void>builder()
				.message(insufficientAuthenticationException.getMessage()).success(false)
				.status(HttpStatus.FORBIDDEN.value()).timestamp(System.currentTimeMillis()).build();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@ExceptionHandler(value = BadCredentialsException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleBadCredentialException(
			BadCredentialsException badCredentialsException) {
		ApiResponseDto<Void> apiResponseDto = ApiResponseDto.<Void>builder()
				.message(badCredentialsException.getMessage()).success(false).status(HttpStatus.FORBIDDEN.value())
				.timestamp(System.currentTimeMillis()).build();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@ExceptionHandler(value = CredentialsExpiredException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleCredentialsExpiredException(
			CredentialsExpiredException credentialsExpiredException) {
		ApiResponseDto<Void> apiResponseDto = ApiResponseDto.<Void>builder()
				.message(credentialsExpiredException.getMessage()).success(false).status(HttpStatus.FORBIDDEN.value())
				.timestamp(System.currentTimeMillis()).build();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ApiResponseDto<Void> apiResponseDto = ApiResponseDto.<Void>builder()
				.message(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()).success(false).status(HttpStatus.BAD_REQUEST.value())
				.timestamp(System.currentTimeMillis()).build();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	

}
