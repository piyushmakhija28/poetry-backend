package com.techdeveloper.lovepoet.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "authorities", schema = "lovepoet")
@NoArgsConstructor
public class Authority implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6161217601010312149L;

	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "authorities_seq", schema = "lovepoet", sequenceName = "authorities_seq")
	@GeneratedValue(generator = "authorities_seq", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "authority", nullable = false, unique = true)
	private String authority;

	public Authority(String authority) {
		super();
		this.authority = authority;
	}

}
