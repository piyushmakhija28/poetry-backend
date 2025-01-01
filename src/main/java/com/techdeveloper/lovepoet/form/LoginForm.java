package com.techdeveloper.lovepoet.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techdeveloper.lovepoet.annotations.group.NotBlankGroup;
import com.techdeveloper.lovepoet.annotations.group.NotEmptyGroup;
import com.techdeveloper.lovepoet.annotations.group.NotNullGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginForm {

	@NotNull(message = "Username cannot be null.",groups = NotNullGroup.class)
	@NotEmpty(message = "Username cannot be empty.",groups = NotEmptyGroup.class)
	@NotBlank(message = "Username cannot be blank.",groups = NotBlankGroup.class)
	private String username;
	
	@NotNull(message = "Password cannot be null.",groups = NotNullGroup.class)
	@NotEmpty(message = "Password cannot be empty.",groups = NotEmptyGroup.class)
	@NotBlank(message = "Password cannot be blank.",groups = NotBlankGroup.class)
	private String password;
	
}
