/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenService implements IJwtTokenService {
	public static final String ISSUER        = "SAM";
	public static final String SUBJECT       = "Spring Boot Rest Example Auth Key";
	public static final String CLAIM_API_KEY = "ApiKey";
	public static final int    HOURS         = 24 * 30 * 6;
	private static      Logger LOG           = LoggerFactory.getLogger(JwtTokenService.class);

	@Autowired
	@Qualifier ("secretKey")
	private SecretKey secretKey;

	@Override
	public String generateApiKey () {
		return UUID.randomUUID().toString();
	}

	@Override
	public String generateJwtToken (final String apiKey) {
		if (apiKey == null) {
			return null;
		}
		JwtBuilder builder = Jwts.builder()
		                         .setIssuer(ISSUER)
		                         .setIssuedAt(new Date())
		                         .setSubject(SUBJECT)
		                         .signWith(secretKey);
		builder = builder.setExpiration(Date.from(LocalDateTime.now()
		                                                       .plusHours(HOURS)
		                                                       .atZone(ZoneId.systemDefault())
		                                                       .toInstant()));
		return builder.claim(CLAIM_API_KEY, apiKey)
		              .compressWith(CompressionCodecs.GZIP) // zipez
		              .compact();
	}

	@Override
	public String getApiKeyFromJwtToken (final String token) throws Exception {

		Jws<Claims> jwt = parseJwtToken(token);
		LOG.debug("Issuer: {}", jwt.getBody().getIssuer());
		LOG.debug("Subject: {}", jwt.getBody().getSubject());
		String apiKey = (String) jwt.getBody().get(CLAIM_API_KEY);
		if (StringUtils.isEmpty(apiKey)) {
			throw new Exception("Invalid token !!! Missing api key!");
		}
		LOG.debug("apiKey: {}", apiKey);
		return apiKey;
	}

	public String resolveJwtToken (HttpServletRequest req) {
		String authHeader = req.getHeader("Authorization");
		if (!StringUtils.isEmpty(authHeader) && authHeader.startsWith("Bearer ")) {
			String bearerToken = authHeader.substring(7, authHeader.length());
			LOG.debug("Bearer Token : {}", bearerToken);
			return bearerToken;
		}
		return null;
	}

	private Jws<Claims> parseJwtToken (final String token) throws Exception {
		try {
			Jws<Claims> jwt = Jwts.parser()
			                      .requireSubject(SUBJECT)
			                      .requireIssuer(ISSUER)
			                      .setSigningKey(secretKey)
			                      .parseClaimsJws(token);
			Date exp = jwt.getBody().getExpiration();
			if (exp != null) {
				LOG.debug("Expiration: {}", exp);
				if (exp.before(new Date())) {
					throw new ExpiredJwtException(null, null, null);
				}
			}

			return jwt;
		} catch (MalformedJwtException e1) {
			throw new Exception("Malformed token !!!");
		} catch (UnsupportedJwtException e2) {
			throw new Exception("Unsupported exception when reading token !!!");
		} catch (ExpiredJwtException e3) {
			throw new Exception("Token is expired !!! Please use login to renew your token !!!");
		} catch (SignatureException e4) {
			throw new Exception("Token signature exception !!!");
		} catch (Exception e4) {
			throw new Exception("General exception when reading token !!!");
		}
	}

	@Override
	public boolean isJwtTokenExpired (final String token) throws Exception {
		Jws<Claims> jwt = parseJwtToken(token);
		Date        exp = jwt.getBody().getExpiration();
		if (exp != null) {
			LOG.debug("Expiration: {}", exp);
			if (exp.before(new Date())) {
				return true;
			}
		}

		return false;
	}
}
