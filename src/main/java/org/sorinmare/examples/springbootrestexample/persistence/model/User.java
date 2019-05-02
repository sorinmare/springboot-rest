/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.persistence.model;

import org.sorinmare.examples.springbootrestexample.constants.RolesConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class User implements Serializable {
	public static final  String TABLE_NAME                 = "tbl_user";
	public static final  String COL_ID                     = "id";
	public static final  String COL_USERNAME               = "username";
	public static final  String COL_USER_DESCRIPTION       = "user_description";
	public static final  String COL_PASSWORD               = "password";
	public static final  String COL_API_TOKEN              = "api_token";
	public static final  String COL_API_KEY                = "api_key";
	public static final  String COL_ROLES                  = "roles";
	public static final  String COL_ENABLED                = "enabled";
	public static final  String COL_ACCOUNTNONEXPIRED      = "accountnonexpired";
	public static final  String COL_ACCOUNTNONLOCKED       = "accountnonlocked";
	public static final  String COL_CREDENTIALSNONEXPIRED  = "credentialsnonexpired";
	public static final  String COL_ACCESS_TO_ALL_PACKAGES = "access_to_all_packages";
	public static final  String COL_PACKAGE_LIST           = "access_package_list";
	private static final long   serialVersionUID           = 1993486276495278683L;

	private String id;

	private String username;

	private String password;

	private String token;

	private String apiKey;

	private List<String> roles;

	private Boolean enabled;

	private Boolean accountNonExpired;

	private Boolean accountNonLocked;

	private Boolean credentialsNonExpired;


	public User () {
		setId(UUID.randomUUID().toString());
		setEnabled(Boolean.TRUE);
		setAccountNonExpired(Boolean.TRUE);
		setAccountNonLocked(Boolean.TRUE);
		setCredentialsNonExpired(Boolean.TRUE);
	}

	public static User buildDummy (final String apiKey) {
		User ret = new User();
		ret.setPassword("dummy");
		ret.setUsername("dummy");
		ret.setApiKey(apiKey);
		ret.getRoles().add(RolesConstants.API_USER);

		return ret;
	}

	public String getUsername () {
		return username;
	}

	public void setUsername (final String username) {
		this.username = username;
	}

	public String getPassword () {
		return password;
	}

	public void setPassword (final String password) {
		this.password = password;
	}

	public Boolean getEnabled () {
		return enabled;
	}

	public void setEnabled (final Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getAccountNonExpired () {
		return accountNonExpired;
	}

	public void setAccountNonExpired (final Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonLocked () {
		return accountNonLocked;
	}

	public void setAccountNonLocked (final Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getCredentialsNonExpired () {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired (final Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public List<String> getRoles () {
		if (roles == null) {
			roles = new ArrayList<>();
		}
		return roles;
	}

	public void setRoles (final String... roles) {
		getRoles().clear();
		getRoles().addAll(Arrays.asList(roles));
	}

	public void setRoles (final List<String> roles) {
		this.roles = roles;
	}

	public String[] getRolesAsArray () {
		String[] ret = new String[getRoles().size()];
		ret = getRoles().toArray(ret);
		return ret;
	}

	public String getId () {
		return id;
	}

	public void setId (final String id) {
		this.id = id;
	}

	public String getToken () {
		return token;
	}

	public void setToken (final String token) {
		this.token = token;
	}

	public String getApiKey () {
		return apiKey;
	}

	public void setApiKey (final String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public boolean equals (final Object obj) {
		// Basic checks.
		if (obj == this) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}

		// Property checks.
		User other = (User) obj;
		if (!this.id.equals(other.id)) {
			return false;
		}


		// All passed.
		return true;
	}

	@Override
	public int hashCode () {
		return getId().hashCode();
	}

	public boolean hasRole (final String role) {
		for (String item : this.getRoles()) {
			if (item.equalsIgnoreCase(role)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasAnyRole (final String... roles) {
		for (String role : roles) {
			if (this.hasRole(role)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasRoles (final String... roles) {
		for (String role : roles) {
			if (!this.hasRole(role)) {
				return false;
			}
		}

		return true;
	}
}
