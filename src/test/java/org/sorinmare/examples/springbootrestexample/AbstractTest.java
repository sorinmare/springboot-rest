/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTest {

	private static final ConcurrentHashMap<Class, Boolean> INITIALIZED = new ConcurrentHashMap<>();
	private static final ThreadLocal<String>               BEARER      = new ThreadLocal<>();

	protected final boolean initialized () {
		final boolean[] absent = {false};
		INITIALIZED.computeIfAbsent(this.getClass(), (klass) -> absent[0] = true);
		return !absent[0];
	}

	protected String getBearer () {
		return BEARER.get();
	}

	protected void setBearer (String bearer) {
		BEARER.set(bearer);
	}
}
