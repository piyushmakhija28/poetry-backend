package com.techdeveloper.lovepoet.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class PoetryDto implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 3490645828042746888L;

	private Long id;
	
	private String title;
	
	private String description;
	
	private String imageUrl;
	
	private String tags;
	
	private String category;
	
	private Long poetryLikesCount;
}
