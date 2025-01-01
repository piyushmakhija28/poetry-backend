package com.techdeveloper.lovepoet.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techdeveloper.lovepoet.annotations.group.LengthGroup;
import com.techdeveloper.lovepoet.annotations.group.NotBlankGroup;
import com.techdeveloper.lovepoet.annotations.group.NotEmptyGroup;
import com.techdeveloper.lovepoet.annotations.group.NotNullGroup;
import com.techdeveloper.lovepoet.annotations.group.RangeGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserForm {

	@NotNull(message = "Firstname cannot be null.",groups = NotNullGroup.class)
	@NotEmpty(message = "Firstname cannot be empty.",groups = NotEmptyGroup.class)
	@NotBlank(message = "Firstname cannot be blank.",groups = NotBlankGroup.class)
	private String firstName;

	@NotNull(message = "Lastname cannot be null.",groups = NotNullGroup.class)
	@NotEmpty(message = "Lastname cannot be empty.",groups = NotEmptyGroup.class)
	@NotBlank(message = "Lastname cannot be blank.",groups = NotBlankGroup.class)
	private String lastName;
	
	@NotNull(message = "Mobile number cannot be null.",groups = NotNullGroup.class)
	@NotEmpty(message = "Mobile number cannot be empty.",groups = NotEmptyGroup.class)
	@NotBlank(message = "Mobile number cannot be blank.",groups = NotBlankGroup.class)
	@Length(min = 10,max = 10,message = "Mobile number lenght must be 10 digits.",groups = LengthGroup.class)
	private String mobileNumber;
	
	@Range(min= 1,max=2,message = "Role id must be 1 or 2.",groups = RangeGroup.class)
	private int role;
	
}
