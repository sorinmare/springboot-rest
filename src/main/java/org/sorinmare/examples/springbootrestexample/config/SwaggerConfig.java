/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {
	private static Logger LOG = LoggerFactory.getLogger(SwaggerConfig.class);

	@Bean
	public Docket api () {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("org.sorinmare.examples.springbootrestexample"))
				//.paths(PathSelectors.ant("/api/foos/*"))
				.build()
				.securitySchemes(securitySchemes())
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo () {
		return new ApiInfo(
				"Spring Boot Rest Example API",
				"A short tutorial for a rest api using spring boot.",
				"1.0",
				"Terms of services",
				new Contact("Sorin Mare", "www.smartapps.ro", "sorin.mare@gmail.com"),
				"API Licence", "API license URL", Collections.emptyList());
	}

	private List<? extends SecurityScheme> securitySchemes () {
		return Arrays.asList(new ApiKey("Bearer", "Authorization", "header"));
	}
}
