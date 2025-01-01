package com.techdeveloper.lovepoet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techdeveloper.lovepoet.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	boolean existsByAuthorityIgnoreCaseAndIdNot(String name, Long id);

	boolean existsByAuthorityIgnoreCase(String name);

}
