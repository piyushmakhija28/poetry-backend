package com.techdeveloper.lovepoet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.techdeveloper.lovepoet.dto.AuthorityDto;
import com.techdeveloper.lovepoet.dto.UserDto;
import com.techdeveloper.lovepoet.form.UpdateUserForm;
import com.techdeveloper.lovepoet.form.UserForm;
import com.techdeveloper.lovepoet.services.AuthorityServices;
import com.techdeveloper.lovepoet.services.UserServices;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/users")
@CrossOrigin(value = "http://localhost:4200")
@AllArgsConstructor
public class UserRestController {

	private final UserServices userServices;

	private final AuthorityServices authorityServices;

	@PostMapping
	public ResponseEntity<ApiResponseDto<Void>> add(
			@Validated(value = ValidationSequence.class) @RequestBody UserForm userForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ApiResponseDto.<Void>builder()
					.message(bindingResult.getAllErrors().get(0).getDefaultMessage()).success(false)
					.status(HttpStatus.BAD_REQUEST.value()).timestamp(System.currentTimeMillis()).build(),
					HttpStatus.BAD_REQUEST);
		}
		ApiResponseDto<Void> apiResponseDto = userServices.add(userForm,true);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ApiResponseDto<Void>> update(@PathVariable Long id,
			@Validated(value = ValidationSequence.class) @RequestBody UpdateUserForm updateUserForm,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ApiResponseDto.<Void>builder()
					.message(bindingResult.getAllErrors().get(0).getDefaultMessage()).success(false)
					.status(HttpStatus.BAD_REQUEST.value()).timestamp(System.currentTimeMillis()).build(),
					HttpStatus.BAD_REQUEST);
		}
		ApiResponseDto<Void> apiResponseDto = userServices.update(id, updateUserForm,true);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ApiResponseDto<UserDto>> get(@PathVariable Long id) {
		ApiResponseDto<UserDto> apiResponseDto = userServices.get(id);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@PutMapping(value = "/authorities/{id}")
	public ResponseEntity<ApiResponseDto<Void>> saveUserAuthorities(@PathVariable Long id,@RequestBody List<Long> authorityIds) {
		ApiResponseDto<Void> apiResponseDto = authorityServices.saveUserAuthorities(id, authorityIds);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<UserDto>>> get() {
		ApiResponseDto<List<UserDto>> apiResponseDto = userServices.get();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@GetMapping(value = "/authorities/{id}")
	public ResponseEntity<ApiResponseDto<List<AuthorityDto>>> getUsersAuthority(@PathVariable Long id) {
		ApiResponseDto<List<AuthorityDto>> apiResponseDto = authorityServices.getUserAuthorities(id);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable Long id) {
		ApiResponseDto<Void> apiResponseDto = userServices.delete(id,true);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@PutMapping(value = "/{id}/{url}")
	public ResponseEntity<ApiResponseDto<Void>> uploadPhoto(@PathVariable Long id,@PathVariable String url){
		ApiResponseDto<Void> apiResponseDto = userServices.uploadPhoto(id,url);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
}
