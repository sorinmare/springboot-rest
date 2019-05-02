/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.rest.exceptions;

public final class CustomResourceNotFoundException extends RuntimeException {

	public CustomResourceNotFoundException () {
		super();
	}

	public CustomResourceNotFoundException (final String message, final Throwable cause) {
		super(message, cause);
	}

	public CustomResourceNotFoundException (final String message) {
		super(message);
	}

	public CustomResourceNotFoundException (final Throwable cause) {
		super(cause);
	}

}
