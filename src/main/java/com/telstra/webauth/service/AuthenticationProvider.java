package com.telstra.webauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class AuthenticationProvider extends DaoAuthenticationProvider {
	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			return super.authenticate(authentication);
		} catch (BadCredentialsException e) {
			userService.failedLoginAttempt(authentication.getName());
			throw e;
		}
	}
}
