package com.techdeveloper.lovepoet.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ApiResponseDto<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4349581064539980747L;

	private T data;

	private String message;

	private boolean success;

	private int status;

	private long timestamp;

}
