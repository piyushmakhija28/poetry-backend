package com.techdeveloper.lovepoet.services.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.techdeveloper.lovepoet.constants.PoetryStatus;
import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.repository.PoetryRepository;
import com.techdeveloper.lovepoet.repository.UserRepository;
import com.techdeveloper.lovepoet.services.DashboardServices;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
class DashboardServicesImpl implements DashboardServices {

	private static final String DELETED_POETRIES_COUNT_FETCHED_SUCCESSFULLY = "Deleted poetries count fetched successfully.";

	private static final String AWAITING_POETRIES_COUNT_FETCHED_SUCCESSFULLY = "Awaiting poetries count fetched successfully.";

	private static final String REJECTED_POETRIES_COUNT_FETCHED_SUCCESSFULLY = "Rejected poetries count fetched successfully.";

	private static final String APPROVED_POETRIES_COUNT_FETCHED_SUCCESSFULLY = "Approved poetries count fetched successfully.";

	private static final String TOTAL_POETRIES_COUNT_FETCHED_SUCCESSFULLY = "Total poetries count fetched successfully.";

	private static final String LOGGED_IN_USERS_COUNT_FETCHED_SUCCESSFULLY = "LoggedIn users count fetched successfully.";

	private static final String DELETED_USERS_COUNT_FETCHED_SUCCESSFULLY = "Deleted users count fetched successfully.";

	private static final String IN_ACTIVE_USERS_COUNT_FETCHED_SUCCESSFULLY = "In Active users count fetched successfully.";

	private static final String ACTIVE_USERS_COUNT_FETCHED_SUCCESSFULLY = "Active users count fetched successfully.";

	private static final String TOTAL_USERS_COUNT_FETCHED_SUCCESSFULLY = "Total users count fetched successfully.";

	private final UserRepository userRepository;
	
	private final PoetryRepository poetryRepository;

	@Override
	public ApiResponseDto<Long> getTotalUsersCount() {
		return ApiResponseDto.<Long>builder().data(userRepository.count())
				.message(TOTAL_USERS_COUNT_FETCHED_SUCCESSFULLY).success(true).status(HttpStatus.OK.value())
				.timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Long> getActiveUsersCount() {
		return ApiResponseDto.<Long>builder().data(userRepository.countByActiveTrueAndDeletedFalse())
				.message(ACTIVE_USERS_COUNT_FETCHED_SUCCESSFULLY).success(true).status(HttpStatus.OK.value())
				.timestamp(System.currentTimeMillis()).build();
	}
	
	@Override
	public ApiResponseDto<Long> getInActiveUsersCount() {
		return ApiResponseDto.<Long>builder().data(userRepository.countByActiveFalseAndDeletedFalse())
				.message(IN_ACTIVE_USERS_COUNT_FETCHED_SUCCESSFULLY).success(true).status(HttpStatus.OK.value())
				.timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Long> getDeletedUsersCount() {
		return ApiResponseDto.<Long>builder().data(userRepository.countByDeletedTrue())
				.message(DELETED_USERS_COUNT_FETCHED_SUCCESSFULLY).success(true).status(HttpStatus.OK.value())
				.timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Long> getLoggedInUsersCount() {
		return ApiResponseDto.<Long>builder().data(userRepository.countByLoggedInTrue())
				.message(LOGGED_IN_USERS_COUNT_FETCHED_SUCCESSFULLY).success(true).status(HttpStatus.OK.value())
				.timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Long> getTotalPoemsCount() {
		return ApiResponseDto.<Long>builder().data(poetryRepository.count())
				.message(TOTAL_POETRIES_COUNT_FETCHED_SUCCESSFULLY).success(true).status(HttpStatus.OK.value())
				.timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Long> getApprovedPoemsCount() {
		return ApiResponseDto.<Long>builder().data(poetryRepository.countByPoetryStatusAndDeletedFalse(PoetryStatus.APPROVED))
				.message(APPROVED_POETRIES_COUNT_FETCHED_SUCCESSFULLY).success(true).status(HttpStatus.OK.value())
				.timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Long> getRejectedPoemsCount() {
		return ApiResponseDto.<Long>builder().data(poetryRepository.countByPoetryStatusAndDeletedFalse(PoetryStatus.REJECTED))
				.message(REJECTED_POETRIES_COUNT_FETCHED_SUCCESSFULLY).success(true).status(HttpStatus.OK.value())
				.timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Long> getAwaitingPoemsCount() {
		return ApiResponseDto.<Long>builder().data(poetryRepository.countByPoetryStatusAndDeletedFalse(PoetryStatus.AWAITING_APPROVAL))
				.message(AWAITING_POETRIES_COUNT_FETCHED_SUCCESSFULLY).success(true).status(HttpStatus.OK.value())
				.timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Long> getDeletedPoemsCount() {
		return ApiResponseDto.<Long>builder().data(poetryRepository.countByDeletedFalse())
				.message(DELETED_POETRIES_COUNT_FETCHED_SUCCESSFULLY).success(true).status(HttpStatus.OK.value())
				.timestamp(System.currentTimeMillis()).build();
	}

}
