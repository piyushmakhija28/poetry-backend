package com.techdeveloper.lovepoet.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.dto.AuthorityDto;
import com.techdeveloper.lovepoet.entity.Authority;
import com.techdeveloper.lovepoet.entity.User;
import com.techdeveloper.lovepoet.entity.UserAuthorities;
import com.techdeveloper.lovepoet.repository.AuthorityRepository;
import com.techdeveloper.lovepoet.repository.UserAuthoritiesRepository;
import com.techdeveloper.lovepoet.repository.UserRepository;
import com.techdeveloper.lovepoet.services.AuthorityServices;
import com.techdeveloper.lovepoet.services.UserServices;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
class AuthorityServicesImpl implements AuthorityServices {

	private static final String USER_AUTHORITIES_SAVED_SUCCESSFULLY = "User authorities saved successfully.";

	private static final String USER_AUTHORITIES_FETCHED_SUCCESSFULLY = "User authorities fetched successfully.";

	private static final String AUTHORITY_DELETED_SUCCESSFULLY = "Authority deleted successfully.";

	private static final String AUTHORITIES_LIST_FETCHED_SUCCESSFULLY = "Authorities list fetched successfully.";

	private static final String AUTHORITY_FOUND_SUCCESSFULLY = "Authority found successfully.";

	private static final String AUTHORITY_DOES_NOT_EXISTS = "Authority does not exists.";

	private static final String AUTHORITY_UPDATED_SUCCESSFULLY = "Authority updated successfully.";

	private static final String AUTHORITY_NAME_ALREADY_EXISTS = "Authority name already exists.";

	private static final String AUTHORITY_ADDED_SUCCESSFULLY = "Authority added successfully.";

	private final AuthorityRepository authorityRepository;
	
	private final UserRepository userRepository;

	private final UserAuthoritiesRepository userAuthoritiesRepository;

	@Override
	public ApiResponseDto<Void> saveAuthority(String name) {
		if(authorityRepository.existsByAuthorityIgnoreCase(name)) {
			return ApiResponseDto.<Void>builder().message(AUTHORITY_ADDED_SUCCESSFULLY).status(HttpStatus.CREATED.value())
					.success(true).timestamp(System.currentTimeMillis()).build();
		}
		Authority authority = new Authority(name);
		authorityRepository.save(authority);
		return ApiResponseDto.<Void>builder().message(AUTHORITY_ADDED_SUCCESSFULLY).status(HttpStatus.CREATED.value())
				.success(true).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Void> updateAuthority(Long id, String name) {
		Optional<Authority> authorityOptional = authorityRepository.findById(id);
		if (!authorityOptional.isEmpty()) {
			if (authorityRepository.existsByAuthorityIgnoreCaseAndIdNot(name, id)) {
				return ApiResponseDto.<Void>builder().message(AUTHORITY_NAME_ALREADY_EXISTS)
						.status(HttpStatus.CONFLICT.value()).success(false).timestamp(System.currentTimeMillis())
						.build();
			}
			Authority authority = authorityOptional.get();
			authority.setAuthority(name);
			authorityRepository.saveAndFlush(authority);
			return ApiResponseDto.<Void>builder().message(AUTHORITY_UPDATED_SUCCESSFULLY).status(HttpStatus.OK.value())
					.success(true).timestamp(System.currentTimeMillis()).build();
		}
		return ApiResponseDto.<Void>builder().message(AUTHORITY_DOES_NOT_EXISTS).status(HttpStatus.NOT_FOUND.value())
				.success(false).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<AuthorityDto> get(Long id) {
		Optional<Authority> authorityOptional = authorityRepository.findById(id);
		if (!authorityOptional.isEmpty()) {
			Authority authority = authorityOptional.get();
			AuthorityDto authorityDto = new AuthorityDto(authority.getId(), authority.getAuthority());
			return ApiResponseDto.<AuthorityDto>builder().data(authorityDto).message(AUTHORITY_FOUND_SUCCESSFULLY)
					.status(HttpStatus.OK.value()).success(true).timestamp(System.currentTimeMillis()).build();
		}
		return ApiResponseDto.<AuthorityDto>builder().message(AUTHORITY_DOES_NOT_EXISTS)
				.status(HttpStatus.NOT_FOUND.value()).success(false).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<List<AuthorityDto>> get() {
		List<Authority> authorities = authorityRepository.findAll(Sort.by(Direction.ASC, "id"));
		List<AuthorityDto> authorityDtoList = new ArrayList<>(authorities.size());
		authorities.forEach(
				authority -> authorityDtoList.add(new AuthorityDto(authority.getId(), authority.getAuthority())));
		return ApiResponseDto.<List<AuthorityDto>>builder().data(authorityDtoList)
				.message(AUTHORITIES_LIST_FETCHED_SUCCESSFULLY).status(HttpStatus.OK.value()).success(true)
				.timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Void> delete(Long id) {
		authorityRepository.deleteById(id);
		return ApiResponseDto.<Void>builder().message(AUTHORITY_DELETED_SUCCESSFULLY).status(HttpStatus.OK.value())
				.success(true).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<List<AuthorityDto>> getUserAuthorities(Long userId) {
		return ApiResponseDto.<List<AuthorityDto>>builder().data(userAuthoritiesRepository.findByAuthorityForId(userId))
				.message(USER_AUTHORITIES_FETCHED_SUCCESSFULLY).status(HttpStatus.OK.value()).success(true)
				.timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Void> saveUserAuthorities(Long id, List<Long> authorityIds) {
		User user = userRepository.getReferenceById(id);
		if(Objects.isNull(user)) {
			return ApiResponseDto.<Void>builder().message(String.format(UserServices.USER_NOT_FOUND_FOR_ID_S, id)).success(false)
					.status(HttpStatus.NOT_FOUND.value()).timestamp(System.currentTimeMillis()).build();
		}
		userAuthoritiesRepository.deleteByAuthorityForId(id);
		List<Authority> authorities = authorityRepository.findAllById(authorityIds);
		List<UserAuthorities> userAuthoritiesList = new ArrayList<>();
		authorities.forEach(authority->{
			UserAuthorities userAuthorities = new UserAuthorities();
			userAuthorities.setAuthorityFor(user);
			userAuthorities.setAuthority(authority);
			userAuthoritiesList.add(userAuthorities);
		});
		userAuthoritiesRepository.saveAll(userAuthoritiesList);
		return ApiResponseDto.<Void>builder().message(USER_AUTHORITIES_SAVED_SUCCESSFULLY).status(HttpStatus.OK.value())
				.success(true).timestamp(System.currentTimeMillis()).build();
	}

}
