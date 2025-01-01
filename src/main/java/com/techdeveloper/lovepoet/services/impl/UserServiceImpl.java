package com.techdeveloper.lovepoet.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.techdeveloper.lovepoet.constants.Role;
import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.dto.UserDto;
import com.techdeveloper.lovepoet.entity.User;
import com.techdeveloper.lovepoet.form.UpdateUserForm;
import com.techdeveloper.lovepoet.form.UserForm;
import com.techdeveloper.lovepoet.repository.UserRepository;
import com.techdeveloper.lovepoet.services.UserServices;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
class UserServiceImpl implements UserServices {

	private static final String PHOTO_UPLOADED_SUCCESSFULLY = "Photo uploaded successfully.";

	private static final String USERNAME_ALREADY_EXISTS_PLEASE_TRY_WITH_ANOTHER_ONE = "Username already exists.Please try with another one.";

	private static final String MOBILE_NUMBER_ALREADY_REGISTERED_PLEASE_TRY_WITH_ANOTHER_ONE = "Mobile Number already registered please try with another one.";

	private static final String EMAIL_ALREADY_REGISTERED_PLEASE_TRY_WITH_ANOTHER_ONE = "Email already registered please try with another one.";

	private static final String USER_FETCHED_SUCCESSFULLY = "User fetched successfully.";

	private static final String USERS_FETCHED_SUCCESSFULLY = "Users fetched successfully.";

	private static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully.";

	private static final String USER_UPDATED_SUCCESSFULLY = "User updated successfully.";

	private static final String USER_REGISTERED_SUCCESSFULLY = "User registered successfully.";

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public ApiResponseDto<Void> add(UserForm userForm, boolean adminFlag) {
		if (userRepository.existsByEmailIgnoreCase(userForm.getEmail())) {
			return ApiResponseDto.<Void>builder().message(EMAIL_ALREADY_REGISTERED_PLEASE_TRY_WITH_ANOTHER_ONE)
					.success(false).status(HttpStatus.CONFLICT.value()).timestamp(System.currentTimeMillis()).build();
		}
		if (userRepository.existsByMobileNumber(userForm.getMobileNumber())) {
			return ApiResponseDto.<Void>builder().message(MOBILE_NUMBER_ALREADY_REGISTERED_PLEASE_TRY_WITH_ANOTHER_ONE)
					.success(false).status(HttpStatus.CONFLICT.value()).timestamp(System.currentTimeMillis()).build();
		}
		if (userRepository.existsByUsernameIgnoreCase(userForm.getUsername())) {
			return ApiResponseDto.<Void>builder().message(USERNAME_ALREADY_EXISTS_PLEASE_TRY_WITH_ANOTHER_ONE)
					.success(false).status(HttpStatus.CONFLICT.value()).timestamp(System.currentTimeMillis()).build();
		}
		User user = new User();
		user.setFirstName(userForm.getFirstName());
		user.setLastName(userForm.getLastName());
		user.setEmail(userForm.getEmail());
		user.setMobileNumber(userForm.getMobileNumber());
		user.setUsername(userForm.getUsername());
		user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
		if (userForm.getRole() == 1) {
			user.setRole(Role.SUPER_ADMIN);
		} else {
			user.setRole(Role.USER);
		}
		if (adminFlag) {
			user.setCreatedBy(userRepository.getReferenceById(1));
		} else {
			user.setCreatedBy(user);
		}
		userRepository.save(user);
		return ApiResponseDto.<Void>builder().message(USER_REGISTERED_SUCCESSFULLY).success(true)
				.status(HttpStatus.CREATED.value()).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Void> update(Long id, UpdateUserForm updateUserForm, boolean adminFlag) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isEmpty()) {
			return ApiResponseDto.<Void>builder().message(String.format(USER_NOT_FOUND_FOR_ID_S, id)).success(false)
					.status(HttpStatus.NOT_FOUND.value()).timestamp(System.currentTimeMillis()).build();
		}
		if (userRepository.existsByMobileNumberAndIdNot(updateUserForm.getMobileNumber(), id)) {
			return ApiResponseDto.<Void>builder().message(MOBILE_NUMBER_ALREADY_REGISTERED_PLEASE_TRY_WITH_ANOTHER_ONE)
					.success(false).status(HttpStatus.CONFLICT.value()).timestamp(System.currentTimeMillis()).build();
		}
		User user = userOptional.get();
		user.setFirstName(updateUserForm.getFirstName());
		user.setLastName(updateUserForm.getLastName());
		user.setMobileNumber(updateUserForm.getMobileNumber());
		if (updateUserForm.getRole() == 1) {
			user.setRole(Role.SUPER_ADMIN);
		} else {
			user.setRole(Role.USER);
		}
		userRepository.saveAndFlush(user);
		return ApiResponseDto.<Void>builder().message(USER_UPDATED_SUCCESSFULLY).success(true)
				.status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<UserDto> get(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isEmpty()) {
			return ApiResponseDto.<UserDto>builder().message(String.format(USER_NOT_FOUND_FOR_ID_S, id)).success(false)
					.status(HttpStatus.NOT_FOUND.value()).timestamp(System.currentTimeMillis()).build();
		}
		User user = userOptional.get();
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setUsername(user.getUsername());
		userDto.setEmail(user.getEmail());
		if (user.getRole().equals(Role.SUPER_ADMIN)) {
			userDto.setRole(1L);
		} else {
			userDto.setRole(2L);
		}
		userDto.setMobileNumber(user.getMobileNumber());
		userDto.setActive(user.isActive());
		userDto.setDeleted(user.isDeleted());
		userDto.setEmailVerified(user.isEmailVerified());
		userDto.setMobileVerified(user.isMobileVerified());
		userDto.setTwoFactorAuthenticationEnabled(user.isTwoFactorAuthenticationEnabled());
		return ApiResponseDto.<UserDto>builder().data(userDto).message(USER_FETCHED_SUCCESSFULLY).success(true)
				.status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<List<UserDto>> get() {
		Long loggedInUser = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserDto> userDtoList = userRepository.findByIdNotAndDeletedFalse(loggedInUser).stream().map(user -> {
					UserDto userDto = new UserDto();
					userDto.setId(user.getId());
					userDto.setFirstName(user.getFirstName());
					userDto.setLastName(user.getLastName());
					userDto.setUsername(user.getUsername());
					userDto.setEmail(user.getEmail());
					if (user.getRole().equals(Role.SUPER_ADMIN)) {
						userDto.setRole(1L);
					} else {
						userDto.setRole(2L);
					}
					userDto.setMobileNumber(user.getMobileNumber());
					userDto.setActive(user.isActive());
					userDto.setDeleted(user.isDeleted());
					userDto.setEmailVerified(user.isEmailVerified());
					userDto.setMobileVerified(user.isMobileVerified());
					userDto.setTwoFactorAuthenticationEnabled(user.isTwoFactorAuthenticationEnabled());
					return userDto;
				}).toList();
		return ApiResponseDto.<List<UserDto>>builder().data(userDtoList).message(USERS_FETCHED_SUCCESSFULLY)
				.success(true).status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Void> delete(Long id, boolean adminFlag) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isEmpty()) {
			return ApiResponseDto.<Void>builder().message(String.format(USER_NOT_FOUND_FOR_ID_S, id)).success(false)
					.status(HttpStatus.NOT_FOUND.value()).timestamp(System.currentTimeMillis()).build();
		}
		User user = userOptional.get();
		user.setDeleted(true);
		userRepository.save(user);
		return ApiResponseDto.<Void>builder().message(USER_DELETED_SUCCESSFULLY).success(true)
				.status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Void> uploadPhoto(Long id, String url) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isEmpty()) {
			return ApiResponseDto.<Void>builder().message(String.format(USER_NOT_FOUND_FOR_ID_S, id)).success(false)
					.status(HttpStatus.NOT_FOUND.value()).timestamp(System.currentTimeMillis()).build();
		}
		User user = userOptional.get();
		user.setPhoto(url);
		userRepository.save(user);
		return ApiResponseDto.<Void>builder().message(PHOTO_UPLOADED_SUCCESSFULLY).success(true)
				.status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
	}

}
