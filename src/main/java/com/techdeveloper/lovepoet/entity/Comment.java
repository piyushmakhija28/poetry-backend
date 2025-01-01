package com.techdeveloper.lovepoet.entity;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

@Entity
@Data
@Table(name = "comments", schema = "lovepoet")
public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1271660093402993158L;

	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "comments_seq", schema = "lovepoet", sequenceName = "comments_seq")
	@GeneratedValue(generator = "comments_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "comment",nullable = false,columnDefinition = "TEXT")
	private String comment;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "commented_by", referencedColumnName = "id", nullable = false)
	private User commentedBy;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "commented_for", referencedColumnName = "id", nullable = true)
	private Comment commentFor;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "poetry_id", referencedColumnName = "id", nullable = false)
	private Poetry poetry;
}
