package com.techdeveloper.lovepoet.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.techdeveloper.lovepoet.constants.Category;
import com.techdeveloper.lovepoet.constants.PoetryStatus;
import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.dto.PoetryDto;
import com.techdeveloper.lovepoet.entity.Poetry;
import com.techdeveloper.lovepoet.entity.PoetryLikes;
import com.techdeveloper.lovepoet.entity.User;
import com.techdeveloper.lovepoet.form.PoetryForm;
import com.techdeveloper.lovepoet.repository.PoetryLikesRepository;
import com.techdeveloper.lovepoet.repository.PoetryRepository;
import com.techdeveloper.lovepoet.repository.UserRepository;
import com.techdeveloper.lovepoet.services.PoetryServices;
import com.techdeveloper.lovepoet.services.UserServices;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
class PoetryServicesImpl implements PoetryServices {

	private static final String POETRY_DELETED_SUCCESSFULLY = "Poetry deleted successfully.";

	private static final String POETRY_LIKED_SUCCESSFULLY = "Poetry liked successfully.";
	
	private static final String POETRY_UNLIKED_SUCCESSFULLY = "Poetry un-liked successfully.";

	private static final String LIST_OF_POETRIES_FETCHED_SUCCESSFULLY = "List of poetries fetched successfully.";

	private static final String POETRY_DETAILS_FETCHED_SUCCESSFULLY = "Poetry details fetched successfully.";

	private static final String POETRY_UPDATED_SUCCESSFULLY_AND_SENT_FOR_APPROVAL = "Poetry updated successfully and sent for approval.";

	private static final String POETRY_ADDED_SUCCESSFULLY_AND_SENT_FOR_APPROVAL = "Poetry added successfully and sent for approval.";

	private final PoetryRepository poetryRepository;

	private final UserRepository userRepository;

	private final PoetryLikesRepository poetryLikesRepository;

	@Override
	public ApiResponseDto<Void> add(PoetryForm poetryForm) {
		Poetry poetry = new Poetry();
		poetry.setCategory(Category.valueOf(poetryForm.getCategory()));
		poetry.setDescription(poetryForm.getDescription());
		poetry.setImageUrl(poetryForm.getImageUrl());
		poetry.setTags(poetryForm.getTags());
		poetry.setTitle(poetryForm.getTitle());
		poetry.setCreatedBy(userRepository.getReferenceById(2L));
		poetryRepository.save(poetry);
		return ApiResponseDto.<Void>builder().message(POETRY_ADDED_SUCCESSFULLY_AND_SENT_FOR_APPROVAL).success(true)
				.status(HttpStatus.CREATED.value()).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Void> update(Long id, PoetryForm poetryForm) {
		Optional<Poetry> poetryOptional = poetryRepository.findById(id);
		if (poetryOptional.isEmpty()) {
			return ApiResponseDto.<Void>builder().message(POETRY_NOT_FOUND).success(false)
					.status(HttpStatus.NOT_FOUND.value()).timestamp(System.currentTimeMillis()).build();
		}
		Poetry poetry = poetryOptional.get();
		poetry.setCategory(Category.valueOf(poetryForm.getCategory()));
		poetry.setDescription(poetryForm.getDescription());
		poetry.setImageUrl(poetryForm.getImageUrl());
		poetry.setTags(poetryForm.getTags());
		poetry.setTitle(poetryForm.getTitle());
		poetry.setPoetryStatus(PoetryStatus.AWAITING_APPROVAL);
		poetryRepository.saveAndFlush(poetry);
		return ApiResponseDto.<Void>builder().message(POETRY_UPDATED_SUCCESSFULLY_AND_SENT_FOR_APPROVAL).success(true)
				.status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<PoetryDto> get(Long id) {
		Optional<Poetry> poetryOptional = poetryRepository.findById(id);
		if (poetryOptional.isEmpty()) {
			return ApiResponseDto.<PoetryDto>builder().message(POETRY_NOT_FOUND).success(false)
					.status(HttpStatus.NOT_FOUND.value()).timestamp(System.currentTimeMillis()).build();
		}
		Poetry poetry = poetryOptional.get();
		Long likesCount = poetryLikesRepository.countByPoetry(poetry);
		PoetryDto poetryDto = new PoetryDto();
		poetryDto.setCategory(poetry.getCategory().name());
		poetryDto.setDescription(poetry.getDescription());
		poetryDto.setId(poetry.getId());
		poetryDto.setImageUrl(poetry.getImageUrl());
		poetryDto.setTags(poetry.getTags());
		poetryDto.setTitle(poetry.getTitle());
		poetryDto.setPoetryLikesCount(likesCount);
		return ApiResponseDto.<PoetryDto>builder().data(poetryDto).message(POETRY_DETAILS_FETCHED_SUCCESSFULLY)
				.success(true).status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<List<PoetryDto>> get() {
		List<PoetryDto> poetryDtoList = poetryRepository.findByDeletedFalse().stream().map(poetry -> {
			Long likesCount = poetryLikesRepository.countByPoetry(poetry);
			PoetryDto poetryDto = new PoetryDto();
			poetryDto.setCategory(poetry.getCategory().name());
			poetryDto.setDescription(poetry.getDescription());
			poetryDto.setId(poetry.getId());
			poetryDto.setImageUrl(poetry.getImageUrl());
			poetryDto.setTags(poetry.getTags());
			poetryDto.setTitle(poetry.getTitle());
			poetryDto.setPoetryLikesCount(likesCount);
			return poetryDto;
		}).toList();
		return ApiResponseDto.<List<PoetryDto>>builder().data(poetryDtoList)
				.message(LIST_OF_POETRIES_FETCHED_SUCCESSFULLY).success(true).status(HttpStatus.OK.value())
				.timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Void> delete(Long id) {
		poetryRepository.deleteById(id);
		return ApiResponseDto.<Void>builder().message(POETRY_DELETED_SUCCESSFULLY).success(true)
				.status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Void> like(Long id, Long likedBy) {
		Optional<Poetry> poetryOptional = poetryRepository.findById(id);
		if (poetryOptional.isEmpty()) {
			return ApiResponseDto.<Void>builder().message(POETRY_NOT_FOUND).success(false)
					.status(HttpStatus.NOT_FOUND.value()).timestamp(System.currentTimeMillis()).build();
		}
		Optional<User> userOptional = userRepository.findById(likedBy);
		if (userOptional.isEmpty()) {
			return ApiResponseDto.<Void>builder().message(String.format(UserServices.USER_NOT_FOUND_FOR_ID_S, likedBy))
					.success(false).status(HttpStatus.NOT_FOUND.value()).timestamp(System.currentTimeMillis()).build();
		}
		Poetry poetry = poetryOptional.get();
		PoetryLikes poetryLikes = new PoetryLikes();
		poetryLikes.setPoetry(poetry);
		poetryLikes.setLikedByUser(userOptional.get());
		poetryLikesRepository.save(poetryLikes);
		return ApiResponseDto.<Void>builder().message(POETRY_LIKED_SUCCESSFULLY).success(true)
				.status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<Void> unlike(Long id, Long unlikedBy) {
		PoetryLikes poetryLikes = poetryLikesRepository.findByPoetryIdAndLikedByUserId(id, unlikedBy);
		if (Objects.nonNull(poetryLikes)) {
			poetryLikesRepository.delete(poetryLikes);
		}
		return ApiResponseDto.<Void>builder().message(POETRY_UNLIKED_SUCCESSFULLY).success(true)
				.status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
	}

	@Override
	public ApiResponseDto<List<PoetryDto>> get(String category) {
		Category categoryType = null;
		if(Category.NATURE.name().equals(category)) {
			categoryType = Category.NATURE;
		}
		if(Category.ROMANTIC.name().equals(category)) {
			categoryType = Category.ROMANTIC;
		}
		if(Category.SAD.name().equals(category)) {
			categoryType = Category.SAD;
		}
		if(Category.MISC.name().equals(category)) {
			categoryType = Category.MISC;
		}
		List<PoetryDto> poetryDtoList = poetryRepository.findByDeletedFalseAndCategory(categoryType).stream().map(poetry -> {
			Long likesCount = poetryLikesRepository.countByPoetry(poetry);
			PoetryDto poetryDto = new PoetryDto();
			poetryDto.setCategory(poetry.getCategory().name());
			poetryDto.setDescription(poetry.getDescription());
			poetryDto.setId(poetry.getId());
			poetryDto.setImageUrl(poetry.getImageUrl());
			poetryDto.setTags(poetry.getTags());
			poetryDto.setTitle(poetry.getTitle());
			poetryDto.setPoetryLikesCount(likesCount);
			return poetryDto;
		}).toList();
		return ApiResponseDto.<List<PoetryDto>>builder().data(poetryDtoList)
				.message(LIST_OF_POETRIES_FETCHED_SUCCESSFULLY).success(true).status(HttpStatus.OK.value())
				.timestamp(System.currentTimeMillis()).build();
	}

}
