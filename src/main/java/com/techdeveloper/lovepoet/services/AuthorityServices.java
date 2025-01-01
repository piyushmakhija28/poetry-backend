package com.techdeveloper.lovepoet.services;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.dto.AuthorityDto;

public interface AuthorityServices {

	@Transactional(rollbackFor = DataAccessException.class,propagation = Propagation.REQUIRES_NEW)
	public ApiResponseDto<Void> saveAuthority(String name);

	@Transactional(rollbackFor = DataAccessException.class,propagation = Propagation.REQUIRED)
	public ApiResponseDto<Void> updateAuthority(Long id,String name);

	@Transactional(rollbackFor = DataAccessException.class,propagation = Propagation.REQUIRED,readOnly = true)
	public ApiResponseDto<AuthorityDto> get(Long id);
	
	@Transactional(rollbackFor = DataAccessException.class,propagation = Propagation.REQUIRED,readOnly = true)
	public ApiResponseDto<List<AuthorityDto>> get();

	@Transactional(rollbackFor = DataAccessException.class,propagation = Propagation.REQUIRES_NEW)
	public ApiResponseDto<Void> delete(Long id);

	@Transactional(rollbackFor = DataAccessException.class,propagation = Propagation.REQUIRED,readOnly = true)
	public ApiResponseDto<List<AuthorityDto>> getUserAuthorities(Long userId);
	
	@Transactional(rollbackFor = DataAccessException.class,propagation = Propagation.REQUIRED)
	public ApiResponseDto<Void> saveUserAuthorities(Long id,List<Long> authorityIds);
}
