package com.techdeveloper.lovepoet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.dto.AuthorityDto;
import com.techdeveloper.lovepoet.services.AuthorityServices;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/authorities")
@AllArgsConstructor
public class AuthoritiesRestController {

	private final AuthorityServices authorityServices;
	
	@PostMapping(value = "/{name}")
	public ResponseEntity<ApiResponseDto<Void>> add(@PathVariable String name) {
		ApiResponseDto<Void> apiResponseDto = authorityServices.saveAuthority(name);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@PutMapping(value = "/{id}/{name}")
	public ResponseEntity<ApiResponseDto<Void>> update(@PathVariable Long id, @PathVariable String name) {
		ApiResponseDto<Void> apiResponseDto = authorityServices.updateAuthority(id, name);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ApiResponseDto<AuthorityDto>> get(@PathVariable Long id) {
		ApiResponseDto<AuthorityDto> apiResponseDto = authorityServices.get(id);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<AuthorityDto>>> get() {
		ApiResponseDto<List<AuthorityDto>> apiResponseDto = authorityServices.get();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable Long id) {
		ApiResponseDto<Void> apiResponseDto = authorityServices.delete(id);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
}
