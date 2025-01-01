package com.techdeveloper.lovepoet.services;

import com.techdeveloper.lovepoet.dto.ApiResponseDto;

public interface DashboardServices {

	public ApiResponseDto<Long> getTotalUsersCount();

	public ApiResponseDto<Long> getActiveUsersCount();

	public ApiResponseDto<Long> getDeletedUsersCount();

	public ApiResponseDto<Long> getLoggedInUsersCount();

	public ApiResponseDto<Long> getTotalPoemsCount();

	public ApiResponseDto<Long> getApprovedPoemsCount();

	public ApiResponseDto<Long> getRejectedPoemsCount();

	public ApiResponseDto<Long> getAwaitingPoemsCount();

	public ApiResponseDto<Long> getDeletedPoemsCount();

	public ApiResponseDto<Long> getInActiveUsersCount();
	
}
