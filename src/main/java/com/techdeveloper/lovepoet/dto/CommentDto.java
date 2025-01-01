package com.techdeveloper.lovepoet.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class CommentDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 490169921224579934L;

	private Long id;
	
	private String comment;
	
	private Long commentById;
	
	private String commentByName;
	
	private Long commentLikesCount;
	
}
