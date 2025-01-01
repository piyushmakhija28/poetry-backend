package com.techdeveloper.lovepoet.entity;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "comment_likes", schema = "lovepoet")
public class CommentLikes implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7246997973877940647L;

	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "comment_likes_seq", schema = "lovepoet", sequenceName = "comment_likes_seq")
	@GeneratedValue(generator = "comment_likes_seq", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "comment_id", referencedColumnName = "id", nullable = false)
	private Comment comment;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "liked_by", referencedColumnName = "id", nullable = false)
	private User likedByUser;
	
}
