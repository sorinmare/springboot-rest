/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

@ApiModel (description = "Foo response with a single foo object")
@JacksonXmlRootElement (localName = "foo")
@JsonInclude (JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties (ignoreUnknown = true)
public class FooDTO {
	@ApiModelProperty (value = "Foo id.",
	                   example = "1")
	@JacksonXmlProperty (isAttribute = true)
	private Long   id;
	@ApiModelProperty (value = "Foo name.",
	                   example = "xzyhhjshdksd")
	@JacksonXmlProperty (isAttribute = true)
	private String name;

	public FooDTO () {
	}

	@Override
	public boolean equals (final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof FooDTO)) {
			return false;
		}
		final FooDTO dto = (FooDTO) o;
		return Objects.equals(getId(), dto.getId());
	}

	@Override
	public int hashCode () {
		return Objects.hash(getId());
	}

	public Long getId () {
		return id;
	}

	public void setId (final Long id) {
		this.id = id;
	}

	public String getName () {
		return name;
	}

	public void setName (final String name) {
		this.name = name;
	}
}
