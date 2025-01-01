package com.techdeveloper.lovepoet.config;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techdeveloper.lovepoet.dto.ApiResponseDto;
import com.techdeveloper.lovepoet.repository.UserAuthoritiesRepository;
import com.techdeveloper.lovepoet.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

	private static final String YOU_ARE_NOT_AUTHORIZED_FOR_THIS_REQUEST_PLEASE_CONNECT_WITH_ADMIN = "You are not authorized for this request. Please connect with admin.";

	private final RequestAttributeSecurityContextRepository requestAttributeSecurityContextRepository;

	private final HandlerExceptionResolver handlerExceptionResolver;

	private final UserRepository userRepository;

	private final UserAuthoritiesRepository userAuthoritiesRepository;

	private final HttpServletResponse httpServletResponse;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		AuthenticationConvertor authenticationConvertor = new AuthenticationConvertor(
				requestAttributeSecurityContextRepository, userRepository, userAuthoritiesRepository,
				httpServletResponse);
		AuthenticationManager authenticationManager = new AuthenticationManager() {
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				if (authentication.isAuthenticated()) {
					return authentication;
				}
				return null;
			}
		};
		ObjectPostProcessor<Object> objectPostProcessor = new ObjectPostProcessor<>() {
			@Override
			public <O extends Object> O postProcess(O object) {
				((BasicAuthenticationFilter) object)
						.setSecurityContextRepository(requestAttributeSecurityContextRepository);
				return object;
			}
		};
		return httpSecurity.securityContext(securityContextConfigurer -> {
			securityContextConfigurer.securityContextRepository(requestAttributeSecurityContextRepository);
		}).formLogin(formLoginConfigurer -> formLoginConfigurer.disable()).authorizeHttpRequests(requestAuthorizer -> {
			requestAuthorizer.requestMatchers("/api/v1/login","/api/v1/user/login", "/api/v1/register","/api/v1/poetries/{id}","/api/v1/poetries/category/{category}").permitAll();
			requestAuthorizer.requestMatchers(HttpMethod.GET, "/api/v1/poetries").permitAll();
			requestAuthorizer.requestMatchers(HttpMethod.GET, "/api/v1/authorities")
					.hasAuthority("GET_ALL_AUTHORITIES");
			requestAuthorizer.requestMatchers(HttpMethod.GET, "/api/v1/authorities/{id}").hasAuthority("GET_AUTHORITY");
			requestAuthorizer.requestMatchers(HttpMethod.DELETE, "/api/v1/authorities/{id}")
					.hasAuthority("DELETE_AUTHORITY");
			requestAuthorizer.requestMatchers(HttpMethod.POST, "/api/v1/authorities/{id}")
					.hasAuthority("ADD_AUTHORITY");
			requestAuthorizer.requestMatchers(HttpMethod.POST, "/api/v1/authorities/{id}/{name}")
					.hasAuthority("UPDATE_AUTHORITY");

//			requestAuthorizer.requestMatchers(HttpMethod.GET, "/api/v1/poetries").hasAuthority("GET_ALL_POETRIES");
//			requestAuthorizer.requestMatchers(HttpMethod.GET, "/api/v1/poetries/{id}").hasAuthority("GET_POETRY");
			requestAuthorizer.requestMatchers(HttpMethod.DELETE, "/api/v1/poetries/{id}").hasAuthority("DELETE_POETRY");
			requestAuthorizer.requestMatchers(HttpMethod.PUT, "/api/v1/poetries/{id}").hasAuthority("UPDATE_POETRY");
			requestAuthorizer.requestMatchers(HttpMethod.POST, "/api/v1/poetries").hasAuthority("ADD_POETRY");
			requestAuthorizer.requestMatchers(HttpMethod.PUT, "/api/v1/poetries/like/{id}").hasAuthority("LIKE_POETRY");
			requestAuthorizer.requestMatchers(HttpMethod.PUT, "/api/v1/poetries/unlike/{id}")
					.hasAuthority("UNLIKE_POETRY");

			requestAuthorizer.requestMatchers(HttpMethod.POST, "/api/v1/comments/poetry/{id}")
					.hasAuthority("ADD_COMMENT");
			requestAuthorizer.requestMatchers(HttpMethod.GET, "/api/v1/comments/poetry/{id}")
					.hasAuthority("GET_COMMENT");
			requestAuthorizer.requestMatchers(HttpMethod.GET, "/api/v1/comments/poetry/comments/{id}")
					.hasAuthority("GET_NESTED_COMMENT");
			requestAuthorizer.requestMatchers(HttpMethod.POST, "/api/v1/comments/like/{id}")
					.hasAuthority("LIKE_COMMENT");
			requestAuthorizer.requestMatchers(HttpMethod.GET, "/api/v1/comments/unlike/{id}")
					.hasAuthority("UNLIKE_COMMENT");

			requestAuthorizer.requestMatchers(HttpMethod.POST, "/api/v1/users").hasAuthority("ADD_USER");
			requestAuthorizer.requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}").hasAuthority("UPDATE_USER");
			requestAuthorizer.requestMatchers(HttpMethod.PUT, "/api/v1/users/authorities/{id}")
					.hasAuthority("UPDATE_AUTHORITIES");
			requestAuthorizer.requestMatchers(HttpMethod.GET, "/api/v1/users/authorities/{id}")
					.hasAuthority("GET_USER_AUTHORITIES");
			requestAuthorizer.requestMatchers(HttpMethod.GET, "/api/v1/users/{id}").hasAuthority("GET_USER");
			requestAuthorizer.requestMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority("GET_ALL_USERS");
			requestAuthorizer.requestMatchers(HttpMethod.DELETE, "/api/v1/users/{id}").hasAuthority("DELETE_USER");

			requestAuthorizer.anyRequest().authenticated();
		}).logout(logoutConfigurer -> logoutConfigurer.disable()).csrf(csrfConfigurer -> csrfConfigurer.disable())
				.cors(corsConfigurer -> corsConfigurer.configurationSource(new CorsConfigurationSource() {
					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
						CorsConfiguration corsConfiguration = new CorsConfiguration();
						corsConfiguration.setAllowedHeaders(List.of("*"));
						corsConfiguration.setAllowedMethods(List.of(HttpMethod.POST.name(), HttpMethod.PUT.name(),
								HttpMethod.GET.name(), HttpMethod.DELETE.name()));
						corsConfiguration.setAllowedOrigins(List.of("*"));
						return corsConfiguration;
					}
				})).httpBasic(httpBasicConfigurer -> httpBasicConfigurer.disable())
				.objectPostProcessor(objectPostProcessor)
				.addFilterAt(new com.techdeveloper.lovepoet.config.AuthenticationFilter(authenticationManager,
						authenticationConvertor, handlerExceptionResolver), BasicAuthenticationFilter.class)
				.exceptionHandling(exceptionHandlingConfigurer -> {
					exceptionHandlingConfigurer.accessDeniedHandler(new AccessDeniedHandler() {
						@Override
						public void handle(HttpServletRequest request, HttpServletResponse response,
								AccessDeniedException accessDeniedException) throws IOException, ServletException {
							log.error("Access denied exception :: ", accessDeniedException);
							ObjectMapper objectMapper = new ObjectMapper();
							ApiResponseDto<Void> apiResponseDto = ApiResponseDto.<Void>builder()
									.message(YOU_ARE_NOT_AUTHORIZED_FOR_THIS_REQUEST_PLEASE_CONNECT_WITH_ADMIN)
									.success(false).status(HttpStatus.UNAUTHORIZED.value())
									.timestamp(System.currentTimeMillis()).build();
							response.setContentType(MediaType.APPLICATION_JSON_VALUE);
							response.setStatus(HttpStatus.UNAUTHORIZED.value());
							response.getWriter().write(objectMapper.writeValueAsString(apiResponseDto));
						}
					});
				}).sessionManagement(sessionManagementCustomizer -> {
					sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.NEVER);
					sessionManagementCustomizer
							.addSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());
				}).build();
	}

}
