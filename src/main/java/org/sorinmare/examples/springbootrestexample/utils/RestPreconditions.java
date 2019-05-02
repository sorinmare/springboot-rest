/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.utils;

import org.sorinmare.examples.springbootrestexample.rest.exceptions.CustomResourceNotFoundException;

/**
 * Simple static methods to be called at the start of your own methods to verify correct arguments and state. If the Precondition fails, an {@link org.springframework.http.HttpStatus} code is thrown
 */
public final class RestPreconditions {

	private RestPreconditions () {
		throw new AssertionError();
	}

	// API

	/**
	 * Check if some value was found, otherwise throw exception.
	 *
	 * @param expression has value true if found, otherwise false
	 *
	 * @throws CustomResourceNotFoundException if expression is false, means value not found.
	 */
	public static void checkFound (final boolean expression) {
		if (!expression) {
			throw new CustomResourceNotFoundException();
		}
	}

	/**
	 * Check if some value was found, otherwise throw exception.
	 *
	 * @param expression has value true if found, otherwise false
	 *
	 * @throws CustomResourceNotFoundException if expression is false, means value not found.
	 */
	public static <T> T checkFound (final T resource) {
		if (resource == null) {
			throw new CustomResourceNotFoundException();
		}

		return resource;
	}

}
