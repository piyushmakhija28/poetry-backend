package com.techdeveloper.lovepoet.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techdeveloper.lovepoet.entity.User;

public interface UserRepository extends JpaRepository<User, Serializable> {

	User findByUsernameAndDeletedFalse(String username);

	User findByEmail(String subject);

	boolean existsByEmailIgnoreCase(String email);

	boolean existsByMobileNumber(String mobileNumber);

	boolean existsByUsernameIgnoreCase(String username);

	boolean existsByMobileNumberAndIdNot(String mobileNumber, Long id);

	List<User> findByIdNotAndDeletedFalse(Long loggedInUser);

	Long countByActiveTrueAndDeletedFalse();

	Long countByDeletedTrue();

	Long countByLoggedInTrue();

	Long countByActiveFalseAndDeletedFalse();

}
