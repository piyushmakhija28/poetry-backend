package com.techdeveloper.lovepoet.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class AuthenticationDto extends UserDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8330980780950996641L;

	private String accessToken;
	
	private List<AuthorityDto> authourities;
	
}
