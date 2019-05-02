/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.services;

import javax.servlet.http.HttpServletRequest;

public interface IJwtTokenService {
	String generateApiKey ();

	String resolveJwtToken (HttpServletRequest req);

	String generateJwtToken (final String apiKey);

	String getApiKeyFromJwtToken (final String token) throws Exception;

	boolean isJwtTokenExpired (final String token) throws Exception;
}
