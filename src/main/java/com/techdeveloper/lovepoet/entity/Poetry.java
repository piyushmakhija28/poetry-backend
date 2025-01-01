package com.techdeveloper.lovepoet.entity;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.techdeveloper.lovepoet.constants.Category;
import com.techdeveloper.lovepoet.constants.PoetryStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "poetries",schema = "lovepoet")
@DynamicInsert
@DynamicUpdate
public class Poetry implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6494570235860948928L;
	
	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "poetries_seq", schema = "lovepoet", sequenceName = "poetries_seq")
	@GeneratedValue(generator = "poetries_seq", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "title",nullable = false,unique = true)
	private String title;

	@Column(name="description",nullable = false,columnDefinition = "TEXT")
	private String description;

	@Column(name="image_url",nullable = false,columnDefinition = "TEXT")
	private String imageUrl;
	
	@Column(name = "is_deleted")
	private boolean deleted=false;
	
	@Column(name = "tags",nullable = false)
	private String tags;
	
	@Column(name = "category",nullable = false)
	@Enumerated(EnumType.STRING)
	private Category category;
	
	@Column(name = "poetry_status",nullable = false)
	@Enumerated(EnumType.STRING)
	private PoetryStatus poetryStatus = PoetryStatus.AWAITING_APPROVAL;
	
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "created_by",referencedColumnName = "id",nullable = false)
	private User createdBy;
	
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by",referencedColumnName = "id")
	private User updatedBy;
	
	@CreationTimestamp
	@Column(name = "created_at",nullable = false)
	private LocalDate createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDate updatedAt;
}
