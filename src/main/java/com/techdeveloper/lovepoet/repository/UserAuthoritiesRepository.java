package com.techdeveloper.lovepoet.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.techdeveloper.lovepoet.dto.AuthorityDto;
import com.techdeveloper.lovepoet.entity.User;
import com.techdeveloper.lovepoet.entity.UserAuthorities;

public interface UserAuthoritiesRepository extends JpaRepository<UserAuthorities, Serializable> {

	@Query(value = "select new com.techdeveloper.lovepoet.dto.AuthorityDto(ua.authority.id,ua.authority.authority) from UserAuthorities ua where ua.authorityFor.id=?1")
	List<AuthorityDto> findByAuthorityForId(Long userId);

	long deleteByAuthorityForId(Long id);

	@Query(value = "select new org.springframework.security.core.authority.SimpleGrantedAuthority(ua.authority.authority) from UserAuthorities ua where ua.authorityFor=?1")
	List<SimpleGrantedAuthority> findByAuthorityFor(User user);

}
