/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.auth;

import org.sorinmare.examples.springbootrestexample.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService {

	@Autowired
	private String apiKey;

	@Override
	public UserDetails loadUserByUsername (final String username) throws UsernameNotFoundException {
		// find user by service
		throw new UsernameNotFoundException(username);
	}

	public UserDetails loadUserByApiKey (final String apiKey) throws UsernameNotFoundException {
		if (!this.apiKey.equals(apiKey)) {
			throw new UsernameNotFoundException(apiKey);
		}
		// find user by apiKey using user service
		User user = User.buildDummy(this.apiKey);
		if (user == null) {
			throw new UsernameNotFoundException(apiKey);
		}

		return new UserPrincipal(user);
	}
}
