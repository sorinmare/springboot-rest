/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sorinmare.examples.springbootrestexample.rest.messages.LoginRQ;
import org.sorinmare.examples.springbootrestexample.rest.messages.LoginRS;
import org.sorinmare.examples.springbootrestexample.services.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping (value = "/api/auth")
@Api (value = "Authentication",
      description = "This API expose all the routes needed in order to login to these rest services.",
      tags = {"Authentication"})
public class AuthRestController {
	private static Logger LOG = LoggerFactory.getLogger(AuthRestController.class);

	@Autowired
	private JwtTokenService jwtTokenService;
	@Autowired
	private String          apiKey;

	@ApiOperation (
			value = "Login",
			notes = "Use this route to login in application and to get a new api token used for other routes authentication",
			response = LoginRS.class
	)
	@PostMapping (value = "/login",
	              consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
	              produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus (HttpStatus.OK)
	@ResponseBody
	public LoginRS init (@RequestBody LoginRQ dto, final HttpServletResponse response) {
		LOG.debug("Api key: {}", apiKey);
		return new LoginRS().withToken(jwtTokenService.generateJwtToken(apiKey));
	}
}
