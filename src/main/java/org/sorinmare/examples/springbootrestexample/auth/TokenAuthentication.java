/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthentication {

	public void setAuthentication (UserDetails userDetails) {
		final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,
		                                                                                          null,
		                                                                                          userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);
	}

}
