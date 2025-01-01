package com.techdeveloper.lovepoet.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name = "system_logs",schema = "lovepoet")
public class SystemLogs implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4287113865348211738L;

	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "system_logs_seq", schema = "lovepoet", sequenceName = "system_logs_seq")
	@GeneratedValue(generator = "system_logs_seq", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "session_log_id", referencedColumnName = "id", nullable = false)
	private SessionLog sessionId;
	
	@Column(name = "url",nullable = false)
	private String url;
	
	@Column(name = "http_method",nullable = false)
	private String httpMethod;

	@Column(name = "requested_at",nullable = false)
	private LocalDateTime requestedAt;
	
	@Column(name = "response_at",nullable = true)
	private LocalDateTime responseAt;
	
	@Column(name = "payload",nullable = true)
	private String payload;
	
	@Column(name = "response",nullable = false)
	private String response;
}
