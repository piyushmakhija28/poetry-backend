package com.techdeveloper.lovepoet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techdeveloper.lovepoet.annotations.sequence.ValidationSequence;
import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.dto.PoetryDto;
import com.techdeveloper.lovepoet.form.PoetryForm;
import com.techdeveloper.lovepoet.services.PoetryServices;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/poetries")
@AllArgsConstructor
public class PoetryRestController {

	private final PoetryServices poetryServices;

	private final RequestAttributeSecurityContextRepository requestAttributeSecurityContextRepository;

	@PostMapping
	public ResponseEntity<ApiResponseDto<Void>> add(
			@Validated(value = ValidationSequence.class) @RequestBody PoetryForm poetryForm,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ApiResponseDto.<Void>builder()
					.message(bindingResult.getAllErrors().get(0).getDefaultMessage()).success(false)
					.status(HttpStatus.BAD_REQUEST.value()).timestamp(System.currentTimeMillis()).build(),
					HttpStatus.BAD_REQUEST);
		}
		ApiResponseDto<Void> apiResponseDto = poetryServices.add(poetryForm);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ApiResponseDto<Void>> update(@PathVariable Long id,
			@Validated(value = ValidationSequence.class) @RequestBody PoetryForm poetryForm,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ApiResponseDto.<Void>builder()
					.message(bindingResult.getAllErrors().get(0).getDefaultMessage()).success(false)
					.status(HttpStatus.BAD_REQUEST.value()).timestamp(System.currentTimeMillis()).build(),
					HttpStatus.BAD_REQUEST);
		}
		ApiResponseDto<Void> apiResponseDto = poetryServices.update(id, poetryForm);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ApiResponseDto<PoetryDto>> get(@PathVariable Long id) {
		ApiResponseDto<PoetryDto> apiResponseDto = poetryServices.get(id);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<PoetryDto>>> get() {
		ApiResponseDto<List<PoetryDto>> apiResponseDto = poetryServices.get();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@GetMapping(value = "/category/{category}")
	public ResponseEntity<ApiResponseDto<List<PoetryDto>>> get(@PathVariable String category) {
		ApiResponseDto<List<PoetryDto>> apiResponseDto = poetryServices.get(category);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable Long id) {
		ApiResponseDto<Void> apiResponseDto = poetryServices.delete(id);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@PutMapping(value = "/like/{id}")
	public ResponseEntity<ApiResponseDto<Void>> like(@PathVariable Long id, HttpServletRequest httpServletRequest) {
		DeferredSecurityContext deferredSecurityContext = requestAttributeSecurityContextRepository
				.loadDeferredContext(httpServletRequest);
		SecurityContext securityContext = deferredSecurityContext.get();
		Authentication authentication = securityContext.getAuthentication();
		ApiResponseDto<Void> apiResponseDto = poetryServices.like(id, (Long) authentication.getPrincipal());
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@PutMapping(value = "/unlike/{id}")
	public ResponseEntity<ApiResponseDto<Void>> unlike(@PathVariable Long id, HttpServletRequest httpServletRequest) {
		DeferredSecurityContext deferredSecurityContext = requestAttributeSecurityContextRepository
				.loadDeferredContext(httpServletRequest);
		SecurityContext securityContext = deferredSecurityContext.get();
		Authentication authentication = securityContext.getAuthentication();
		ApiResponseDto<Void> apiResponseDto = poetryServices.unlike(id, (Long) authentication.getPrincipal());
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
}
