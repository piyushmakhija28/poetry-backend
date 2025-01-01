package com.techdeveloper.lovepoet.entity;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

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
@Table(name = "user_authorities", schema = "lovepoet")
public class UserAuthorities implements Serializable, GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5776577934762366168L;

	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "user_authorities_seq", schema = "lovepoet", sequenceName = "user_authorities_seq")
	@GeneratedValue(generator = "user_authorities_seq", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "authority_for", referencedColumnName = "id", nullable = false)
	private User authorityFor;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "authority_id", referencedColumnName = "id", nullable = false)
	private Authority authority;

	@Override
	public String getAuthority() {
		return authority.getAuthority();
	}

}
