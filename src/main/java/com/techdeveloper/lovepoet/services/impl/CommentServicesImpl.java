package com.techdeveloper.lovepoet.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.dto.CommentDto;
import com.techdeveloper.lovepoet.entity.Comment;
import com.techdeveloper.lovepoet.entity.CommentLikes;
import com.techdeveloper.lovepoet.entity.Poetry;
import com.techdeveloper.lovepoet.entity.User;
import com.techdeveloper.lovepoet.form.CommentForm;
import com.techdeveloper.lovepoet.repository.CommentLikesRepository;
import com.techdeveloper.lovepoet.repository.CommentRepository;
import com.techdeveloper.lovepoet.repository.PoetryRepository;
import com.techdeveloper.lovepoet.repository.UserRepository;
import com.techdeveloper.lovepoet.services.PoetryServices;
import com.techdeveloper.lovepoet.services.PoetryServices.CommentServices;
import com.techdeveloper.lovepoet.services.UserServices;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
class CommentServicesImpl implements CommentServices {

	private static final String COMMENTS_FETCHED_SUCCESSFULLY = "Comments Fetched Successfully.";

	private static final String COMMENT_ADDED_SUCCESSFULLY = "Comment Added Successfully.";

	public static final String COMMENT_NOT_FOUND_FOR_ID_S = "Comment not found for id %s.";

	private static final String COMMENT_LIKED_SUCCESSFULLY = "Comment liked successfully.";

	private static final String COMMENT_UNLIKED_SUCCESSFULLY = "Comment un-liked successfully.";

	private final CommentRepository commentRepository;

	private final CommentLikesRepository commentLikesRepository;

	private final UserRepository userRepository;

	private final PoetryRepository poetryRepository;

	@Override
	public ApiResponseDto<Void> addComment(Long poetryId, CommentForm commentForm, Long commentBy) {
		Optional<User> commentByOptional = userRepository.findById(commentBy);
		if (commentByOptional.isEmpty()) {
			return ApiResponseDto.<Void>builder()
					.message(String.format(UserServices.USER_NOT_FOUND_FOR_ID_S, commentBy)).success(false)
					.status(HttpStatus.NOT_FOUND.value()).timestamp(System.currentTimeMillis()).build();
		}
		Optional<Poetry> poetryOptional = poetryRepository.findById(poetryId);
		if (poetryOptional.isEmpty()) {
			return ApiResponseDto.<Void>builder().message(PoetryServices.POETRY_NOT_FOUND).success(false)
					.status(HttpStatus.NOT_FOUND.value()).timestamp(System.currentTimeMillis()).build();
		}
		Comment commentFor = null;
		if (Objects.nonNull(commentForm.getCommentFor())) {
			Optional<Comment> commentForOptional = commentRepository.findById(commentForm.getCommentFor());
			if (commentByOptional.isEmpty()) {
				return ApiResponseDto.<Void>builder()
						.message(String.format(COMMENT_NOT_FOUND_FOR_ID_S, commentForm.getCommentFor())).success(false)
						.status(HttpStatus.NOT_FOUND.value()).timestamp(System.currentTimeMillis()).build();
			}
			commentFor = commentForOptional.get();
		}
		Comment comment = new Comment();
		comment.setComment(commentForm.getComment());
		comment.setCommentedBy(commentByOptional.get());
		comment.setPoetry(poetryOptional.get());
		comment.setCommentFor(commentFor);
		commentRepository.save(comment);
		return ApiResponseDto.<Void>builder().message(COMMENT_ADDED_SUCCESSFULLY).success(true)
				.status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<List<CommentDto>> getCommentsOfPoetry(Long poetryId) {
		Optional<Poetry> poetryOptional = poetryRepository.findById(poetryId);
		if (poetryOptional.isEmpty()) {
			return ApiResponseDto.<List<CommentDto>>builder().message(PoetryServices.POETRY_NOT_FOUND).success(false)
					.status(HttpStatus.NOT_FOUND.value()).timestamp(System.currentTimeMillis()).build();
		}
		List<CommentDto> commentDtos = commentRepository.findByCommentForIsNullAndPoetry(poetryOptional.get()).stream()
				.map(comment -> {
					CommentDto commentDto = new CommentDto();
					commentDto.setComment(comment.getComment());
					commentDto.setCommentById(comment.getId());
					User commentedBy = comment.getCommentedBy();
					commentDto
							.setCommentByName(commentedBy.getFirstName().concat(" ").concat(commentedBy.getLastName()));
					commentDto.setCommentLikesCount(commentLikesRepository.countByComment(comment));
					return commentDto;
				}).toList();
		return ApiResponseDto.<List<CommentDto>>builder().data(commentDtos).message(COMMENTS_FETCHED_SUCCESSFULLY)
				.success(true).status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<List<CommentDto>> getNestedCommentFor(Long commentId) {
		Optional<Comment> commentOptional = commentRepository.findById(commentId);
		if (commentOptional.isEmpty()) {
			return ApiResponseDto.<List<CommentDto>>builder()
					.message(String.format(COMMENT_NOT_FOUND_FOR_ID_S, commentId)).success(false)
					.status(HttpStatus.NOT_FOUND.value()).timestamp(System.currentTimeMillis()).build();
		}
		List<CommentDto> commentDtos = commentRepository.findByCommentFor(commentOptional.get()).stream()
				.map(comment -> {
					CommentDto commentDto = new CommentDto();
					commentDto.setComment(comment.getComment());
					commentDto.setCommentById(comment.getId());
					User commentedBy = comment.getCommentedBy();
					commentDto
							.setCommentByName(commentedBy.getFirstName().concat(" ").concat(commentedBy.getLastName()));
					commentDto.setCommentLikesCount(commentLikesRepository.countByComment(comment));
					return commentDto;
				}).toList();
		return ApiResponseDto.<List<CommentDto>>builder().data(commentDtos).message(COMMENTS_FETCHED_SUCCESSFULLY)
				.success(true).status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Void> like(Long id, Long likedBy) {
		Optional<Comment> commentOptional = commentRepository.findById(id);
		if (commentOptional.isEmpty()) {
			return ApiResponseDto.<Void>builder().message(String.format(COMMENT_NOT_FOUND_FOR_ID_S, id)).success(false)
					.status(HttpStatus.NOT_FOUND.value()).timestamp(System.currentTimeMillis()).build();
		}
		Optional<User> userOptional = userRepository.findById(likedBy);
		if (userOptional.isEmpty()) {
			return ApiResponseDto.<Void>builder().message(String.format(UserServices.USER_NOT_FOUND_FOR_ID_S, likedBy))
					.success(false).status(HttpStatus.NOT_FOUND.value()).timestamp(System.currentTimeMillis()).build();
		}
		CommentLikes commentLikes = new CommentLikes();
		commentLikes.setComment(commentOptional.get());
		commentLikes.setLikedByUser(userOptional.get());
		commentLikesRepository.save(commentLikes);
		return ApiResponseDto.<Void>builder().message(COMMENT_LIKED_SUCCESSFULLY).success(true)
				.status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Void> unlike(Long id, Long unlikedBy) {
		CommentLikes commentLikes = commentLikesRepository.findByCommentIdAndLikedByUserId(id, unlikedBy);
		if (Objects.nonNull(commentLikes)) {
			commentLikesRepository.delete(commentLikes);
		}
		return ApiResponseDto.<Void>builder().message(COMMENT_UNLIKED_SUCCESSFULLY).success(true)
				.status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
	}

}
