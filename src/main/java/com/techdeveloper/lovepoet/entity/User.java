package com.techdeveloper.lovepoet.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.techdeveloper.lovepoet.constants.Role;

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
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "users", schema = "lovepoet")
@DynamicInsert
@DynamicUpdate
public class User implements Serializable, UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5044615070144160386L;

	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "user_seq", schema = "lovepoet", sequenceName = "user_seq")
	@GeneratedValue(generator = "user_seq", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "is_email_verified")
	private boolean emailVerified = false;

	@Column(name = "mobile_number", nullable = false, unique = true, length = 10)
	private String mobileNumber;

	@Column(name = "is_mobile_verified")
	private boolean mobileVerified = false;

	@Column(name = "is_two_factor_authentication_enabled")
	private boolean twoFactorAuthenticationEnabled = false;

	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "is_deleted")
	private boolean deleted = false;
	
	@Column(name = "is_logged_in")
	private boolean loggedIn = false;

	@Column(name = "is_active")
	private boolean active = false;

	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "created_by", referencedColumnName = "id")
	private User createdBy;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by", referencedColumnName = "id")
	private User updatedBy;
	
	@CreationTimestamp
	@Column(name = "created_at",nullable = false)
	private LocalDate createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDate updatedAt;

	@Column(name = "photo",columnDefinition = "TEXT")
	private String photo;
	
	@Transient
	private List<UserAuthorities> userAuthorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userAuthorities;
	}
}
