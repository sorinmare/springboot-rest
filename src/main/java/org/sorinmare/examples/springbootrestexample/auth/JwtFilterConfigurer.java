/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.auth;

import org.sorinmare.examples.springbootrestexample.services.JwtTokenService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	private JwtTokenService      jwtTokenService;
	private AppUserDetailService appUserDetailService;
	private TokenAuthentication  tokenAuthentication;

	public JwtFilterConfigurer (JwtTokenService jwtTokenService, AppUserDetailService appUserDetailService, TokenAuthentication tokenAuthentication) {
		this.jwtTokenService = jwtTokenService;
		this.appUserDetailService = appUserDetailService;
		this.tokenAuthentication = tokenAuthentication;
	}

	@Override
	public void configure (final HttpSecurity http) throws Exception {
		JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenService, appUserDetailService, tokenAuthentication);
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
