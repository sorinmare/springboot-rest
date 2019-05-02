/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.config;

import org.sorinmare.examples.springbootrestexample.auth.AppUserDetailService;
import org.sorinmare.examples.springbootrestexample.auth.JwtFilterConfigurer;
import org.sorinmare.examples.springbootrestexample.auth.TokenAuthentication;
import org.sorinmare.examples.springbootrestexample.constants.RolesConstants;
import org.sorinmare.examples.springbootrestexample.services.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtTokenService      jwtTokenService;
	@Autowired
	private AppUserDetailService appUserDetailService;
	@Autowired
	private TokenAuthentication  tokenAuthentication;

	@Bean
	@Override
	protected AuthenticationManager authenticationManager () throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure (HttpSecurity http) throws Exception {
		http.httpBasic().disable()
		    .csrf().disable()
		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		    .and().headers().frameOptions().sameOrigin() // for h2c
		    .and()
		    .authorizeRequests()
		    .antMatchers("/api/auth/**").permitAll() // api auth
		    .antMatchers("/h2c/**").permitAll() // for h2c
		    .antMatchers("/swagger**", "/swagger*/**", "/webjars/**", "/v2/**").permitAll() //for swagger
		    .antMatchers(HttpMethod.DELETE, "/api/foos/**").hasAnyAuthority(RolesConstants.API_USER)
		    .antMatchers(HttpMethod.PUT, "/api/foos/**").hasAnyAuthority(RolesConstants.API_USER)
		    .antMatchers(HttpMethod.GET, "/api/foos/**").permitAll()
		    .and()
		    .authorizeRequests()
		    .anyRequest().authenticated()
		    .and()
		    .apply(new JwtFilterConfigurer(jwtTokenService, appUserDetailService, tokenAuthentication));
	}
}
