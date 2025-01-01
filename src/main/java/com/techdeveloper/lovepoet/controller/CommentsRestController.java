package com.techdeveloper.lovepoet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techdeveloper.lovepoet.annotations.sequence.ValidationSequence;
import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.dto.CommentDto;
import com.techdeveloper.lovepoet.form.CommentForm;
import com.techdeveloper.lovepoet.services.PoetryServices;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/comments")
@AllArgsConstructor
public class CommentsRestController {

	private PoetryServices.CommentServices commentServices;

	private final RequestAttributeSecurityContextRepository requestAttributeSecurityContextRepository;

	@PostMapping(value = "/poetry/{id}")
	public ResponseEntity<ApiResponseDto<Void>> addComment(@PathVariable Long id,
			@Validated(value = ValidationSequence.class) @RequestBody CommentForm commentForm,
			BindingResult bindingResult, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ApiResponseDto.<Void>builder()
					.message(bindingResult.getAllErrors().get(0).getDefaultMessage()).success(false)
					.status(HttpStatus.BAD_REQUEST.value()).timestamp(System.currentTimeMillis()).build(),
					HttpStatus.BAD_REQUEST);
		}
		DeferredSecurityContext deferredSecurityContext = requestAttributeSecurityContextRepository
				.loadDeferredContext(httpServletRequest);
		SecurityContext securityContext = deferredSecurityContext.get();
		Authentication authentication = securityContext.getAuthentication();
		ApiResponseDto<Void> apiResponseDto = commentServices.addComment(id, commentForm,
				(Long) authentication.getPrincipal());
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

	@GetMapping(value = "/poetry/{id}")
	public ResponseEntity<ApiResponseDto<List<CommentDto>>> getCommentsOfPoetry(@PathVariable Long id) {
		ApiResponseDto<List<CommentDto>> apiResponseDto = commentServices.getCommentsOfPoetry(id);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@GetMapping(value = "/poetry/comments/{id}")
	public ResponseEntity<ApiResponseDto<List<CommentDto>>> getCommentsOfComment(@PathVariable Long id) {
		ApiResponseDto<List<CommentDto>> apiResponseDto = commentServices.getNestedCommentFor(id);
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@PutMapping(value = "/like/{id}")
	public ResponseEntity<ApiResponseDto<Void>> like(@PathVariable Long id, HttpServletRequest httpServletRequest) {
		DeferredSecurityContext deferredSecurityContext = requestAttributeSecurityContextRepository
				.loadDeferredContext(httpServletRequest);
		SecurityContext securityContext = deferredSecurityContext.get();
		Authentication authentication = securityContext.getAuthentication();
		ApiResponseDto<Void> apiResponseDto = commentServices.like(id, (Long) authentication.getPrincipal());
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}
	
	@PutMapping(value = "/unlike/{id}")
	public ResponseEntity<ApiResponseDto<Void>> unlike(@PathVariable Long id, HttpServletRequest httpServletRequest) {
		DeferredSecurityContext deferredSecurityContext = requestAttributeSecurityContextRepository
				.loadDeferredContext(httpServletRequest);
		SecurityContext securityContext = deferredSecurityContext.get();
		Authentication authentication = securityContext.getAuthentication();
		ApiResponseDto<Void> apiResponseDto = commentServices.unlike(id, (Long) authentication.getPrincipal());
		return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
	}

}
