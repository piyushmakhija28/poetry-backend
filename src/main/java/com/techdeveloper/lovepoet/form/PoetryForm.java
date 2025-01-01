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
public class PoetryForm {

	@NotNull(message = "Poetry title cannot be null.", groups = NotNullGroup.class)
	@NotEmpty(message = "Poetry title cannot be empty.", groups = NotEmptyGroup.class)
	@NotBlank(message = "Poetry title cannot be blank.", groups = NotBlankGroup.class)
	private String title;

	@NotNull(message = "Poetry description cannot be null.", groups = NotNullGroup.class)
	@NotEmpty(message = "Poetry description cannot be empty.", groups = NotEmptyGroup.class)
	@NotBlank(message = "Poetry description cannot be blank.", groups = NotBlankGroup.class)
	private String description;

	@NotNull(message = "Poetry tags cannot be null.", groups = NotNullGroup.class)
	@NotEmpty(message = "Poetry tags cannot be empty.", groups = NotEmptyGroup.class)
	@NotBlank(message = "Poetry tags cannot be blank.", groups = NotBlankGroup.class)
	private String tags;

	@NotNull(message = "Poetry image url cannot be null.", groups = NotNullGroup.class)
	@NotEmpty(message = "Poetry image url cannot be empty.", groups = NotEmptyGroup.class)
	@NotBlank(message = "Poetry image url cannot be blank.", groups = NotBlankGroup.class)
	private String imageUrl;

	@NotNull(message = "Poetry category cannot be null.", groups = NotNullGroup.class)
	@NotEmpty(message = "Poetry category cannot be empty.", groups = NotEmptyGroup.class)
	@NotBlank(message = "Poetry category cannot be blank.", groups = NotBlankGroup.class)
	private String category;

}
