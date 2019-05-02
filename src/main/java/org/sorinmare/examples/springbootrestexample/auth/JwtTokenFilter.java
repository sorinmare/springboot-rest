/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sorinmare.examples.springbootrestexample.services.JwtTokenService;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class JwtTokenFilter extends GenericFilterBean {
	private static Logger               LOG = LoggerFactory.getLogger(JwtTokenFilter.class);
	private        JwtTokenService      jwtTokenService;
	private        AppUserDetailService appUserDetailService;
	private        TokenAuthentication  tokenAuthentication;

	public JwtTokenFilter (JwtTokenService jwtTokenService, AppUserDetailService appUserDetailService, TokenAuthentication tokenAuthentication) {
		this.jwtTokenService = jwtTokenService;
		this.appUserDetailService = appUserDetailService;
		this.tokenAuthentication = tokenAuthentication;
	}

	@Override
	public void doFilter (final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		String path = ((HttpServletRequest) request).getRequestURI();
		if (!path.contains("/api/")) { // if not api url then ignore filter
			chain.doFilter(request, response);
			return;
		}
		LOG.debug("JwtTokenFilter ...");
		try {
			String jwtToken = jwtTokenService.resolveJwtToken((HttpServletRequest) request);
			if (jwtToken != null && !jwtTokenService.isJwtTokenExpired(jwtToken)) {
				String apiKey = jwtTokenService.getApiKeyFromJwtToken(jwtToken);
				tokenAuthentication.setAuthentication(appUserDetailService.loadUserByApiKey(apiKey));
			}
		} catch (Exception exc) {
			StringWriter w = new StringWriter();
			exc.printStackTrace(new PrintWriter(w));
			LOG.error(w.toString());
		}
		chain.doFilter(request, response);
	}
}
