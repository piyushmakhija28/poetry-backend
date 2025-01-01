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
public class CommentForm {

	@NotNull(message = "Comment cannot be null.",groups = NotNullGroup.class)
	@NotEmpty(message = "Comment cannot be empty.",groups = NotEmptyGroup.class)
	@NotBlank(message = "Comment cannot be blank.",groups = NotBlankGroup.class)
	private String comment;
	
	private Long commentFor;

}
