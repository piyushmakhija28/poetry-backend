package com.techdeveloper.lovepoet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techdeveloper.lovepoet.annotations.sequence.ValidationSequence;
import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.form.UserForm;
import com.techdeveloper.lovepoet.services.UserServices;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/register")
@AllArgsConstructor
public class RegistrationRestController {

	private final UserServices userServices;
	
	@PostMapping
	public ResponseEntity<ApiResponseDto<Void>> register(@Validated(ValidationSequence.class) @RequestBody UserForm userForm){
		ApiResponseDto<Void> apiResponseDto = userServices.add(userForm,false);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
}
