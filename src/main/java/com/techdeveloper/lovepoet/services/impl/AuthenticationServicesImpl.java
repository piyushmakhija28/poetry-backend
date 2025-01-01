package com.techdeveloper.lovepoet.services.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techdeveloper.lovepoet.config.Authentication;
import com.techdeveloper.lovepoet.constants.Role;
import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.dto.AuthenticationDto;
import com.techdeveloper.lovepoet.dto.AuthorityDto;
import com.techdeveloper.lovepoet.entity.User;
import com.techdeveloper.lovepoet.repository.UserAuthoritiesRepository;
import com.techdeveloper.lovepoet.repository.UserRepository;
import com.techdeveloper.lovepoet.services.AuthenticationServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Service
@AllArgsConstructor
class AuthenticationServicesImpl implements AuthenticationServices {

	private static final String LOGIN_SUCCESSFULL = "Login Successfull.";

	private static final String EMAIL_IS_NOT_YET_VERIFIED_PLEASE_VERIFY_EMAIL_ID_BEFORE_LOGIN = "Email is not yet verified please verify email id before login.";

	private static final String INVALID_CREDENTIALS = "Invalid Credentials.";

	private final UserRepository userRepository;

	private final UserAuthoritiesRepository userAuthoritiesRepository;

	private final RequestAttributeSecurityContextRepository requestAttributeSecurityContextRepository;

	private final HttpServletRequest httpServletRequest;

	private final HttpServletResponse httpServletResponse;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	@SneakyThrows(JsonProcessingException.class)
	public ApiResponseDto<AuthenticationDto> login(String username, String password) {
		User user = userRepository.findByUsernameAndDeletedFalse(username);
		if (Objects.isNull(user) || user.getRole().equals(Role.USER)) {
			return ApiResponseDto.<AuthenticationDto>builder().message(INVALID_CREDENTIALS)
					.status(HttpStatus.FORBIDDEN.value()).success(false).timestamp(System.currentTimeMillis()).build();
		}
		if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
			return ApiResponseDto.<AuthenticationDto>builder().message(INVALID_CREDENTIALS)
					.status(HttpStatus.FORBIDDEN.value()).success(false).timestamp(System.currentTimeMillis()).build();
		}
		if (!user.isEmailVerified()) {
			return ApiResponseDto.<AuthenticationDto>builder()
					.message(EMAIL_IS_NOT_YET_VERIFIED_PLEASE_VERIFY_EMAIL_ID_BEFORE_LOGIN)
					.status(HttpStatus.FORBIDDEN.value()).success(false).timestamp(System.currentTimeMillis()).build();
		}
		user.setLoggedIn(true);
		userRepository.saveAndFlush(user);
		List<AuthorityDto> authourities = userAuthoritiesRepository.findByAuthorityForId(user.getId());
		AuthenticationDto authenticationDto = new AuthenticationDto();
		authenticationDto.setActive(user.isActive());
		authenticationDto.setEmail(user.getEmail());
		authenticationDto.setFirstName(user.getFirstName());
		authenticationDto.setId(user.getId());
		authenticationDto.setLastName(user.getLastName());
		authenticationDto.setMobileNumber(user.getMobileNumber());
		authenticationDto.setMobileVerified(user.isMobileVerified());
		authenticationDto.setEmailVerified(user.isEmailVerified());
		if (user.getRole().equals(Role.SUPER_ADMIN)) {
			authenticationDto.setRole(1L);
		} else {
			authenticationDto.setRole(2L);
		}
		authenticationDto.setTwoFactorAuthenticationEnabled(user.isTwoFactorAuthenticationEnabled());
		authenticationDto.setAuthourities(authourities);
		authenticationDto.setPhoto(user.getPhoto());
		Authentication authentication = new Authentication(user, userAuthoritiesRepository, true);
		if (!requestAttributeSecurityContextRepository.containsContext(httpServletRequest)) {
			SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
			securityContext.setAuthentication(authentication);
			requestAttributeSecurityContextRepository.saveContext(securityContext, httpServletRequest,
					httpServletResponse);
		}
		String accessToken = JWT.create().withSubject(user.getEmail()).withIssuedAt(Instant.now())
				.withExpiresAt(Instant.now().plus(10, ChronoUnit.MINUTES)).withIssuer("www.lovepoet.com")
				.withClaim("userId", user.getId()).withPayload(new ObjectMapper().writeValueAsString(authentication))
				.sign(Algorithm.HMAC256("www.lovepoet.com"));
		authenticationDto.setAccessToken(accessToken);
		return ApiResponseDto.<AuthenticationDto>builder().data(authenticationDto).message(LOGIN_SUCCESSFULL)
				.status(HttpStatus.OK.value()).success(true).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public void logout(Long id) {
		User user = userRepository.findById(id).get();
		user.setLoggedIn(false);
		userRepository.saveAndFlush(user);
	}

	@Override
	@SneakyThrows(JsonProcessingException.class)
	public ApiResponseDto<AuthenticationDto> userLogin(String username, String password) {
		User user = userRepository.findByUsernameAndDeletedFalse(username);
		if (Objects.isNull(user) || user.getRole().equals(Role.SUPER_ADMIN)) {
			return ApiResponseDto.<AuthenticationDto>builder().message(INVALID_CREDENTIALS)
					.status(HttpStatus.FORBIDDEN.value()).success(false).timestamp(System.currentTimeMillis()).build();
		}
		if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
			return ApiResponseDto.<AuthenticationDto>builder().message(INVALID_CREDENTIALS)
					.status(HttpStatus.FORBIDDEN.value()).success(false).timestamp(System.currentTimeMillis()).build();
		}
		if (!user.isEmailVerified()) {
			return ApiResponseDto.<AuthenticationDto>builder()
					.message(EMAIL_IS_NOT_YET_VERIFIED_PLEASE_VERIFY_EMAIL_ID_BEFORE_LOGIN)
					.status(HttpStatus.FORBIDDEN.value()).success(false).timestamp(System.currentTimeMillis()).build();
		}
		user.setLoggedIn(true);
		userRepository.saveAndFlush(user);
		List<AuthorityDto> authourities = userAuthoritiesRepository.findByAuthorityForId(user.getId());
		AuthenticationDto authenticationDto = new AuthenticationDto();
		authenticationDto.setActive(user.isActive());
		authenticationDto.setEmail(user.getEmail());
		authenticationDto.setFirstName(user.getFirstName());
		authenticationDto.setId(user.getId());
		authenticationDto.setLastName(user.getLastName());
		authenticationDto.setMobileNumber(user.getMobileNumber());
		authenticationDto.setMobileVerified(user.isMobileVerified());
		authenticationDto.setEmailVerified(user.isEmailVerified());
		if (user.getRole().equals(Role.SUPER_ADMIN)) {
			authenticationDto.setRole(1L);
		} else {
			authenticationDto.setRole(2L);
		}
		authenticationDto.setTwoFactorAuthenticationEnabled(user.isTwoFactorAuthenticationEnabled());
		authenticationDto.setAuthourities(authourities);
		authenticationDto.setPhoto(user.getPhoto());
		Authentication authentication = new Authentication(user, userAuthoritiesRepository, true);
		if (!requestAttributeSecurityContextRepository.containsContext(httpServletRequest)) {
			SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
			securityContext.setAuthentication(authentication);
			requestAttributeSecurityContextRepository.saveContext(securityContext, httpServletRequest,
					httpServletResponse);
		}
		String accessToken = JWT.create().withSubject(user.getEmail()).withIssuedAt(Instant.now())
				.withExpiresAt(Instant.now().plus(10, ChronoUnit.MINUTES)).withIssuer("www.lovepoet.com")
				.withClaim("userId", user.getId()).withPayload(new ObjectMapper().writeValueAsString(authentication))
				.sign(Algorithm.HMAC256("www.lovepoet.com"));
		authenticationDto.setAccessToken(accessToken);
		return ApiResponseDto.<AuthenticationDto>builder().data(authenticationDto).message(LOGIN_SUCCESSFULL)
				.status(HttpStatus.OK.value()).success(true).timestamp(System.currentTimeMillis()).build();
	}

}
