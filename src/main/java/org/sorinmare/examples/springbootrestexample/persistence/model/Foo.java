/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.persistence.model;

import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;

@Entity
public class Foo implements Serializable {
	private static final long serialVersionUID = 1135947769332234557L;

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;

	@Column (nullable = false)
	private String name;

	@Version
	private long version;

	public Foo () {
		super();
		this.name = RandomStringUtils.randomAlphabetic(10);
	}

	public Foo (final String name) {
		super();
		this.name = name;
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

	public long getVersion () {
		return version;
	}

	public void setVersion (long version) {
		this.version = version;
	}

	@Override
	public int hashCode () {
		final int prime  = 31;
		int       result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals (final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Foo other = (Foo) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString () {
		final StringBuilder builder = new StringBuilder();
		builder.append("Foo [name=").append(name).append("]");
		return builder.toString();
	}

}
