package com.techdeveloper.lovepoet.services;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.dto.UserDto;
import com.techdeveloper.lovepoet.form.UpdateUserForm;
import com.techdeveloper.lovepoet.form.UserForm;

public interface UserServices {
	
	public static final String USER_NOT_FOUND_FOR_ID_S = "User not found for id %s.";

	@Transactional(rollbackFor = DataAccessException.class,propagation = Propagation.REQUIRES_NEW)
	public ApiResponseDto<Void> add(UserForm userForm,boolean adminFlag);
	
	@Transactional(rollbackFor = DataAccessException.class,propagation = Propagation.REQUIRED)
	public ApiResponseDto<Void> update(Long id,UpdateUserForm updateUserForm,boolean adminFlag);

	@Transactional(rollbackFor = DataAccessException.class,propagation = Propagation.REQUIRED,readOnly = true)
	public ApiResponseDto<UserDto> get(Long id);
	
	@Transactional(rollbackFor = DataAccessException.class,propagation = Propagation.REQUIRED,readOnly = true)
	public ApiResponseDto<List<UserDto>> get();
	
	@Transactional(rollbackFor = DataAccessException.class,propagation = Propagation.REQUIRES_NEW)
	public ApiResponseDto<Void> delete(Long id,boolean adminFlag);

	@Transactional(rollbackFor = DataAccessException.class,propagation = Propagation.REQUIRED)
	public ApiResponseDto<Void> uploadPhoto(Long id, String url);
	
}
