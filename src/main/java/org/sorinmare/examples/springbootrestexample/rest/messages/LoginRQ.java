/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.rest.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

@ApiModel (description = "Standard login request object")
@JacksonXmlRootElement
@JsonInclude (JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties (ignoreUnknown = true)
public class LoginRQ {
	@ApiModelProperty (value = "Username",
	                   example = "demo")
	@JacksonXmlProperty (isAttribute = true)
	@NotEmpty
	private String username;
	@ApiModelProperty (value = "Password",
	                   example = "your password")
	@JacksonXmlProperty (isAttribute = true)
	@NotEmpty
	private String password;

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
}
