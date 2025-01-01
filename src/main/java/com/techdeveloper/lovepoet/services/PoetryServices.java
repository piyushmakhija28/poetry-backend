package com.techdeveloper.lovepoet.services;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.dto.CommentDto;
import com.techdeveloper.lovepoet.dto.PoetryDto;
import com.techdeveloper.lovepoet.form.CommentForm;
import com.techdeveloper.lovepoet.form.PoetryForm;

public interface PoetryServices {

	public static final String POETRY_NOT_FOUND = "Poetry not found.";
	
	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRES_NEW)
	public ApiResponseDto<Void> add(PoetryForm poetryForm);

	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
	public ApiResponseDto<Void> update(Long id, PoetryForm poetryForm);

	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED, readOnly = true)
	public ApiResponseDto<PoetryDto> get(Long id);

	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED, readOnly = true)
	public ApiResponseDto<List<PoetryDto>> get();

	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRES_NEW)
	public ApiResponseDto<Void> delete(Long id);

	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRES_NEW)
	public ApiResponseDto<Void> like(Long id, Long likedBy);

	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRES_NEW)
	public ApiResponseDto<Void> unlike(Long id, Long unlikedBy);

	public interface CommentServices {

		@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRES_NEW)
		public ApiResponseDto<Void> addComment(Long poetryId, CommentForm commentForm, Long commentBy);

		@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED, readOnly = true)
		public ApiResponseDto<List<CommentDto>> getCommentsOfPoetry(Long poetryId);

		@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED, readOnly = true)
		public ApiResponseDto<List<CommentDto>> getNestedCommentFor(Long commentId);

		@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRES_NEW)
		public ApiResponseDto<Void> like(Long id, Long likedBy);

		@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRES_NEW)
		public ApiResponseDto<Void> unlike(Long id, Long unlikedBy);
	}

	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED, readOnly = true)
	public ApiResponseDto<List<PoetryDto>> get(String category);

}
