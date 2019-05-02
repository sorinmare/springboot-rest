/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.auth;

import org.sorinmare.examples.springbootrestexample.persistence.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {
	private User user;

	public UserPrincipal (User user) {
		this.user = user;
	}

	@Override
	public boolean isEnabled () {
		return getUser().getEnabled();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities () {
		return AuthorityUtils.createAuthorityList(getUser().getRolesAsArray());
	}

	@Override
	public String getPassword () {
		return getUser().getPassword();
	}

	@Override
	public String getUsername () {
		return getUser().getUsername();
	}

	@Override
	public boolean isAccountNonExpired () {
		return getUser().getAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked () {
		return getUser().getAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired () {
		return getUser().getCredentialsNonExpired();
	}

	public User getUser () {
		return user;
	}

	public void setUser (final User user) {
		this.user = user;
	}
}
