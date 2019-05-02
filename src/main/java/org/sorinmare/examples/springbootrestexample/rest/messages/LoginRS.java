/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.rest.messages;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.sorinmare.examples.springbootrestexample.constants.FormatConstants;
import org.sorinmare.examples.springbootrestexample.rest.adapters.ZonedDateTimeAdapter;
import org.sorinmare.examples.springbootrestexample.services.JwtTokenService;

import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;


@ApiModel (description = "Standard authentication response object")
@JacksonXmlRootElement
@JsonInclude (JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties (ignoreUnknown = true)
public class LoginRS {
	@ApiModelProperty (value = "Returned api token that can be used for authentication in other routes")
	@JsonProperty
	@JacksonXmlProperty
	@NotEmpty
	private String        token;
	@ApiModelProperty (value = "The date until this token is valid. After this date token will be considered expired.")
	@JsonProperty
	@JsonFormat (shape = JsonFormat.Shape.STRING,
	             pattern = FormatConstants.DATE_TIME_ZONE_FMT)
	@JacksonXmlProperty
	@XmlJavaTypeAdapter (ZonedDateTimeAdapter.class)
	@NotEmpty
	private ZonedDateTime validUntil;

	public LoginRS () {
		this.validUntil = ZonedDateTime.now().plusHours(JwtTokenService.HOURS);
	}

	public String getToken () {
		return token;
	}

	public void setToken (final String token) {
		this.token = token;
	}

	public ZonedDateTime getValidUntil () {
		return validUntil;
	}

	public void setValidUntil (final ZonedDateTime validUntil) {
		this.validUntil = validUntil;
	}

	public LoginRS withToken (final String token) {
		setToken(token);
		return this;
	}

	public LoginRS withValidUntil (final ZonedDateTime validUntil) {
		setValidUntil(validUntil);
		return this;
	}
}
