/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.rest.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@ApiModel (description = "Standard success \\<response\\>")
@XmlRootElement (name = "response")
@XmlAccessorType (XmlAccessType.FIELD)
@JsonInclude (JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties (ignoreUnknown = true)
public class SuccessRS {
	@ApiModelProperty (value = "Indicates success of operation.",
	                   example = "\\<success/\\>")
	@XmlElement
	@JsonProperty
	private Success success = new Success();

	public Success getSuccess () {
		return success;
	}

	public void setSuccess (final Success success) {
		this.success = success;
	}
}
