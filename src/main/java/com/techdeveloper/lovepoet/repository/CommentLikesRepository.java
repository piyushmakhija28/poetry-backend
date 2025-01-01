package com.techdeveloper.lovepoet.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techdeveloper.lovepoet.entity.Comment;
import com.techdeveloper.lovepoet.entity.CommentLikes;

public interface CommentLikesRepository extends JpaRepository<CommentLikes, Serializable>{

	Long countByComment(Comment comment);

	CommentLikes findByCommentIdAndLikedByUserId(Long id, Long commentBy);

}
