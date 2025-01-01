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
@Table(name = "poetry_likes", schema = "lovepoet")
public class PoetryLikes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6078837217027328079L;

	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "poetry_likes_seq", schema = "lovepoet", sequenceName = "poetry_likes_seq")
	@GeneratedValue(generator = "poetry_likes_seq", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "poetry_id", referencedColumnName = "id", nullable = false)
	private Poetry poetry;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "liked_by", referencedColumnName = "id", nullable = false)
	private User likedByUser;

}
