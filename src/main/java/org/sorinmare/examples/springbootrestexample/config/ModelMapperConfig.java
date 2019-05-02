/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.config;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	private static Logger LOG = LoggerFactory.getLogger(ModelMapperConfig.class);

	@Bean
	public ModelMapper getModelMapper () {
		LOG.debug("model mapper init ...");
		return new ModelMapper();
	}
}
