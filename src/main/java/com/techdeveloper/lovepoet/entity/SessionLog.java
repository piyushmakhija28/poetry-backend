package com.techdeveloper.lovepoet.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "session_logs",schema = "lovepoet")
public class SessionLog implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7428884542925375998L;
	
	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "session_logs_seq", schema = "lovepoet", sequenceName = "session_logs_seq")
	@GeneratedValue(generator = "session_logs_seq", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "session_id",nullable = false)
	private String sessionId;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "session_for", referencedColumnName = "id", nullable = false)
	private User user;
	
	@Column(name = "session_created_at",nullable = false)
	@CreationTimestamp
	private LocalDateTime sessionCreationTime;
	
	@Column(name = "session_expired_at",nullable = true)
	@UpdateTimestamp
	private LocalDateTime sessionExpirationTime;
}
