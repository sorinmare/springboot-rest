/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.config;

import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Configuration
@ComponentScan
public class SecretKeyConfig {
	private static Logger LOG = LoggerFactory.getLogger(SecretKeyConfig.class);

	@Bean (name = "secretKey")
	public SecretKey loadSecretKey () {
		SecretKey key = null;
		try {
			URI    uriSecretKey     = getClass().getClassLoader().getResource("secretKey.key").toURI();
			String secretKeyContent = new String(Files.readAllBytes(Paths.get(uriSecretKey)));
			key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyContent));
			LOG.debug("Loaded SecretKey: {}", secretKeyContent);
		} catch (Exception exc) {
			StringWriter w = new StringWriter();
			exc.printStackTrace(new PrintWriter(w));
			LOG.error(w.toString());
		}

		assert (key != null);
		return key;
	}

	@Bean
	public String getApiKey () {
		return "557c8e69-5ce4-4da1-8046-96b8cd6df0ab";
	}
}
