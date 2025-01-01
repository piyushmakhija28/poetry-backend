package com.techdeveloper.lovepoet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.services.DashboardServices;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/dashboard")
@AllArgsConstructor
public class DashboardRestController {

	private final DashboardServices dashboardServices;

	@GetMapping(value = "/count/users")
	public ResponseEntity<ApiResponseDto<Long>> getTotalUsersCount() {
		ApiResponseDto<Long> apiResponseDto = dashboardServices.getTotalUsersCount();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@GetMapping(value = "/count/users/active")
	public ResponseEntity<ApiResponseDto<Long>> getActiveUsersCount() {
		ApiResponseDto<Long> apiResponseDto = dashboardServices.getActiveUsersCount();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@GetMapping(value = "/count/users/inactive")
	public ResponseEntity<ApiResponseDto<Long>> getInActiveUsersCount() {
		ApiResponseDto<Long> apiResponseDto = dashboardServices.getInActiveUsersCount();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@GetMapping(value = "/count/users/deleted")
	public ResponseEntity<ApiResponseDto<Long>> getDeletedUsersCount() {
		ApiResponseDto<Long> apiResponseDto = dashboardServices.getDeletedUsersCount();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@GetMapping(value = "/count/users/loggedin")
	public ResponseEntity<ApiResponseDto<Long>> getLoggedInUsersCount() {
		ApiResponseDto<Long> apiResponseDto = dashboardServices.getLoggedInUsersCount();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@GetMapping(value = "/count/poems")
	public ResponseEntity<ApiResponseDto<Long>> getTotalPoemsCount() {
		ApiResponseDto<Long> apiResponseDto = dashboardServices.getTotalPoemsCount();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@GetMapping(value = "/count/poems/approved")
	public ResponseEntity<ApiResponseDto<Long>> getApprovedPoemsCount() {
		ApiResponseDto<Long> apiResponseDto = dashboardServices.getApprovedPoemsCount();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@GetMapping(value = "/count/poems/rejected")
	public ResponseEntity<ApiResponseDto<Long>> getRejectedPoemsCount() {
		ApiResponseDto<Long> apiResponseDto = dashboardServices.getRejectedPoemsCount();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@GetMapping(value = "/count/poems/awaiting")
	public ResponseEntity<ApiResponseDto<Long>> getAwaitingPoemsCount() {
		ApiResponseDto<Long> apiResponseDto = dashboardServices.getAwaitingPoemsCount();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@GetMapping(value = "/count/poems/deleted")
	public ResponseEntity<ApiResponseDto<Long>> getDeletedPoemsCount() {
		ApiResponseDto<Long> apiResponseDto = dashboardServices.getDeletedPoemsCount();
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
}
