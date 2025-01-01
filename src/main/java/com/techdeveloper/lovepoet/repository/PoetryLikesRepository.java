package com.techdeveloper.lovepoet.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techdeveloper.lovepoet.entity.Poetry;
import com.techdeveloper.lovepoet.entity.PoetryLikes;

public interface PoetryLikesRepository extends JpaRepository<PoetryLikes, Serializable> {

	PoetryLikes findByPoetryIdAndLikedByUserId(Long id, Long userId);

	Long countByPoetry(Poetry poetry);

}
