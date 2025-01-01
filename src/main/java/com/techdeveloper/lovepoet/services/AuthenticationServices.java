package com.techdeveloper.lovepoet.services;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.dto.AuthenticationDto;

public interface AuthenticationServices {

	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
	public ApiResponseDto<AuthenticationDto> login(String username, String password);

	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
	public void logout(Long id);

	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
	public ApiResponseDto<AuthenticationDto> userLogin(String username, String password);
}
