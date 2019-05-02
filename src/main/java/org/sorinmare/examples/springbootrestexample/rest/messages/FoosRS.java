/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.rest.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.sorinmare.examples.springbootrestexample.rest.dto.FooDTO;

import java.util.List;

@ApiModel (description = "Foo response with a list of foo objects")
@JacksonXmlRootElement
@JsonInclude (JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties (ignoreUnknown = true)
public class FoosRS {

	@ApiModelProperty (value = "Number of returned foos",
	                   example = "2")
	@JsonProperty
	@JacksonXmlProperty (isAttribute = true)
	private Integer count;

	@ApiModelProperty (value = "Foo object list")
	@JsonProperty ("foos")
	@JacksonXmlElementWrapper (localName = "foos")
	@JacksonXmlProperty (localName = "foo")
	private List<FooDTO> foos;


	public FoosRS (final List<FooDTO> items) {
		this.foos = items;
		this.count = getFoos().size();
	}

	public Integer getCount () {
		return count;
	}

	public void setCount (final Integer count) {
		this.count = count;
	}

	public List<FooDTO> getFoos () {
		return foos;
	}

	public void setFoos (final List<FooDTO> foos) {
		this.foos = foos;
	}
}
