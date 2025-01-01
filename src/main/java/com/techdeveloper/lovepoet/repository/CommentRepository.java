package com.techdeveloper.lovepoet.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techdeveloper.lovepoet.entity.Comment;
import com.techdeveloper.lovepoet.entity.Poetry;

public interface CommentRepository extends JpaRepository<Comment, Serializable> {

	List<Comment> findByCommentForIsNullAndPoetry(Poetry poetry);

	List<Comment> findByCommentFor(Comment comment);

}
