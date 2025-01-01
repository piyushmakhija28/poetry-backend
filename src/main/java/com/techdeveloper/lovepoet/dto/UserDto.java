package com.techdeveloper.lovepoet.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class UserDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3060332566933154632L;

	private Long id;
	
	private String firstName;

	private String lastName;
	
	private String email;
	
	private String mobileNumber;
	
	private Long role;
	
	private String username;
	
	private boolean emailVerified;
	
	private boolean mobileVerified;
	
	private boolean twoFactorAuthenticationEnabled;
	
	private boolean active;
	
	private boolean deleted;
	
	private String photo;
	
}
