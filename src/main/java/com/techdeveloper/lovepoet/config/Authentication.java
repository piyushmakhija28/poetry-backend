package com.techdeveloper.lovepoet.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.techdeveloper.lovepoet.entity.User;
import com.techdeveloper.lovepoet.repository.UserAuthoritiesRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Authentication implements org.springframework.security.core.Authentication {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3960388646923319831L;

	private final User user;

	private final UserAuthoritiesRepository userAuthoritiesRepository;

	private boolean isAuthenticated = false;

	@Override
	public String getName() {
		return user.getFirstName().concat(" ").concat(user.getLastName());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userAuthoritiesRepository.findByAuthorityFor(user);
	}

	@Override
	public Object getCredentials() {
		return user.getPassword();
	}

	@Override
	public Object getDetails() {
		return user.getEmail();
	}

	@Override
	public Object getPrincipal() {
		return user.getId();
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated = isAuthenticated;
	}

}
