package com.techdeveloper.lovepoet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.techdeveloper.lovepoet.constants.Role;
import com.techdeveloper.lovepoet.entity.Authority;
import com.techdeveloper.lovepoet.entity.User;
import com.techdeveloper.lovepoet.entity.UserAuthorities;
import com.techdeveloper.lovepoet.repository.AuthorityRepository;
import com.techdeveloper.lovepoet.repository.UserAuthoritiesRepository;
import com.techdeveloper.lovepoet.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class })
public class GauravProjectApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private UserAuthoritiesRepository userAuthoritiesRepository;

	@Bean
	RequestAttributeSecurityContextRepository requestAttributeSecurityContextRepository() {
		return new RequestAttributeSecurityContextRepository();
	}

	public static void main(String[] args) {
		SpringApplication.run(GauravProjectApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(5);
	}

	@Override
	@Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRES_NEW)
	public void run(String... args) throws Exception {
		if (userRepository.count() == 0) {
			log.info("no user presents in database so creating super admin");
			User user = new User();
			user.setFirstName("Gaurav");
			user.setLastName("Kaklani");
			user.setActive(true);
			user.setCreatedBy(user);
			user.setDeleted(false);
			user.setEmail("gauravkaklani0514@gmail.com");
			user.setEmailVerified(true);
			user.setMobileNumber("");
			user.setMobileVerified(true);
			user.setUsername("admin");
			user.setPassword(bCryptPasswordEncoder().encode("admin@12345"));
			user.setRole(Role.SUPER_ADMIN);
			user.setTwoFactorAuthenticationEnabled(false);
			user=userRepository.saveAndFlush(user);
		} else {
			log.info("database is not empty means user already there.");
		}
		if (authorityRepository.count() == 0) {
			List<Authority> authorities = List
					.of("GET_ALL_AUTHORITIES", "GET_AUTHORITY", "DELETE_AUTHORITY", "ADD_AUTHORITY", "UPDATE_AUTHORITY",
							"GET_ALL_POETRIES", "GET_POETRY", "DELETE_POETRY", "UPDATE_POETRY", "ADD_POETRY",
							"LIKE_POETRY", "UNLIKE_POETRY", "ADD_COMMENT", "GET_COMMENT", "GET_NESTED_COMMENT",
							"LIKE_COMMENT", "UNLIKE_COMMENT", "ADD_USER", "UPDATE_USER", "UPDATE_AUTHORITIES",
							"GET_USER_AUTHORITIES", "GET_USER", "GET_ALL_USERS", "DELETE_USER")
					.stream().map(Authority::new).toList();
			authorities=authorityRepository.saveAllAndFlush(authorities);
			List<UserAuthorities> userAuthoritiesList = new ArrayList<>(authorities.size());
			authorities.forEach(authority->{
				UserAuthorities userAuthorities = new UserAuthorities();
				userAuthorities.setAuthority(authority);
				userAuthorities.setAuthorityFor(userRepository.getReferenceById(1L));
				userAuthoritiesList.add(userAuthorities);
			});
			userAuthoritiesRepository.saveAll(userAuthoritiesList);
		}
	}

}
