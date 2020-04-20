/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.rest;

import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sorinmare.examples.springbootrestexample.persistence.model.Foo;
import org.sorinmare.examples.springbootrestexample.persistence.services.FooService;
import org.sorinmare.examples.springbootrestexample.rest.dto.FooDTO;
import org.sorinmare.examples.springbootrestexample.rest.exceptions.CustomResourceNotFoundException;
import org.sorinmare.examples.springbootrestexample.rest.messages.FoosRS;
import org.sorinmare.examples.springbootrestexample.rest.messages.SuccessRS;
import org.sorinmare.examples.springbootrestexample.utils.PageResource;
import org.sorinmare.examples.springbootrestexample.utils.RestPreconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping (value = "/api/foos")
@Api (value = "Foo",
      description = "This API expose some dummy routes.",
      tags = {"Foo"})
public class FooRestController {
	public static final  String      SWAGGER_AUTHENTICATION_SCHEME = "Bearer";
	private static final int         INIT_SIZE                     = 100;
	private static       Logger      LOG                           = LoggerFactory.getLogger(FooRestController.class);
	@Autowired
	private              FooService  service;
	@Autowired
	private              ModelMapper modelMapper;


	@ApiOperation (
			value = "Get foo",
			notes = "Use this route to get a single foo by id from database and return will contain a custom ETag",
			response = FooDTO.class
	)
	@GetMapping (value = "/{id}/custom-etag",
	             produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<FooDTO> findByIdWithETagReturn (@PathVariable ("id") final Long id, final HttpServletResponse response) {
		try {
			Foo foo = RestPreconditions.checkFound(service.findById(id));
			return ResponseEntity.ok()
			                     .eTag(Long.toString(foo.getVersion())) // this will be ignored because it is overriden by etag filter
			                     .body(convertToDto(foo));
		} catch (CustomResourceNotFoundException exc) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foo Not Found", exc);
		}

	}

	@ApiOperation (
			value = "Get foo",
			notes = "Use this route to get a single foo by id from database",
			response = FooDTO.class
	)
	@GetMapping (value = "/{id}",
	             produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public FooDTO findById (@PathVariable ("id") final Long id, final HttpServletResponse response) {
		try {
			Foo foo = RestPreconditions.checkFound(service.findById(id));
			return convertToDto(foo);
		} catch (CustomResourceNotFoundException exc) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foo Not Found", exc);
		}

	}

	@ApiOperation (
			value = "Get foo by page",
			notes = "Use this route to get a paginated list of foos from database",
			response = FooDTO.class
	)
	@GetMapping (value = "/paginated",
	             params = {"page", "size"},
	             produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public PageResource<FooDTO> findPaginated (@RequestParam ("page") @ApiParam (value = "Page number (int)",
	                                                                             example = "0") final int page,
	                                           @RequestParam ("size") @ApiParam (value = "Page size (int)",
	                                                                             example = "10") final int size,
	                                           final UriComponentsBuilder uriBuilder,
	                                           final HttpServletResponse response) {
		final Page<Foo> resultPage = service.findPaginated(page, size);
		if (page > resultPage.getTotalPages()) {
			throw new CustomResourceNotFoundException();
		}

		List<FooDTO> fooDTOS = resultPage.getContent()
		                                 .stream()
		                                 .map(this::convertToDto)
		                                 .collect(Collectors.toList());

		return new PageResource<>(new PageImpl<>(fooDTOS, PageRequest.of(page, size), resultPage.getTotalElements()),
		                          "page",
		                          "size");
	}

	@ApiOperation (
			value = "Get foo by page with pageable",
			notes = "Use this route to get a paginated list of foos from database",
			response = FooDTO.class
	)
	@ApiImplicitParams ({
			                    @ApiImplicitParam (name = "page",
			                                       dataType = "integer",
			                                       paramType = "query",
			                                       value = "Results page you want to retrieve (0..N)"),
			                    @ApiImplicitParam (name = "size",
			                                       dataType = "integer",
			                                       paramType = "query",
			                                       value = "Number of records per page."),
			                    @ApiImplicitParam (name = "sort",
			                                       allowMultiple = true,
			                                       dataType = "string",
			                                       paramType = "query",
			                                       value = "Sorting criteria in the format: property(,asc|desc) " +
					                                       "Default sort order is ascending " +
					                                       "Multiple sort criteria are supported")
	                    })
	@GetMapping (value = "/pageable",
	             produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public PageResource<FooDTO> findPaginatedWithPageable (@ApiIgnore Pageable pageable,
	                                                       final UriComponentsBuilder uriBuilder,
	                                                       final HttpServletResponse response) {
		final Page<Foo> resultPage = service.findPaginated(pageable);
		if (pageable.getPageNumber() > resultPage.getTotalPages()) {
			throw new CustomResourceNotFoundException();
		}

		List<FooDTO> fooDTOS = resultPage.getContent()
		                                 .stream()
		                                 .map(this::convertToDto)
		                                 .collect(Collectors.toList());

		return new PageResource<>(new PageImpl<>(fooDTOS,
		                                         PageRequest.of(resultPage.getNumber(), resultPage.getSize()),
		                                         resultPage.getTotalElements()),
		                          "page",
		                          "size");
	}

	@ApiOperation (
			value = "Get all foos",
			notes = "Use this route to get a list of all foos from database",
			response = FoosRS.class
	)
	@GetMapping (produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public FoosRS findAll () {
		return new FoosRS(service.findAll()
		                         .stream()
		                         .map(this::convertToDto).collect(Collectors.toList()));
	}


	/*	WRITE	 */
	@ApiOperation (
			value = "Init foo database",
			notes = "Use this route to populate database with random foos",
			response = SuccessRS.class,
			authorizations = {
					@Authorization (value = FooRestController.SWAGGER_AUTHENTICATION_SCHEME)
			}
	)
	@PostMapping (value = "/init",
	              produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus (HttpStatus.CREATED)
	public SuccessRS init (final HttpServletResponse response) {
		for (int i = 0; i < INIT_SIZE; i++) {
			service.save(new Foo());
		}
		LOG.debug("dummy foo elements created ...");

		return new SuccessRS();
	}

	@ApiOperation (
			value = "Create foo",
			notes = "Use this route to create a new foo",
			response = FooDTO.class,
			authorizations = {
					@Authorization (value = FooRestController.SWAGGER_AUTHENTICATION_SCHEME)
			}
	)
	@RequestMapping (method = RequestMethod.POST,
	                 consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
	                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseEntity<FooDTO> createFoo (@RequestBody FooDTO dto) {
		Preconditions.checkNotNull(dto);
		dto.setId(null);
		Foo foo        = convertToEntity(dto);
		Foo fooCreated = service.save(foo);
		return ResponseEntity.status(HttpStatus.CREATED)
		                     .body(convertToDto(fooCreated));
	}

	@ApiOperation (
			value = "Update foo",
			notes = "Use this route to update an existing foo",
			response = FooDTO.class,
			authorizations = {
					@Authorization (value = FooRestController.SWAGGER_AUTHENTICATION_SCHEME)
			}
	)
	@PutMapping (value = "/{id}",
	             consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
	             produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus (HttpStatus.OK)
	public ResponseEntity<FooDTO> updateFoo (@PathVariable ("id") final Long id, @RequestBody final FooDTO resource) {
		Preconditions.checkNotNull(resource);
		resource.setId(id);
		RestPreconditions.checkFound(service.findById(resource.getId()));
		Foo foo = service.save(convertToEntity(resource));
		return ResponseEntity.ok()
		                     .body(convertToDto(foo));
	}

	@ApiOperation (
			value = "Delete foo",
			notes = "Use this route to delete an existing foo",
			response = SuccessRS.class,
			authorizations = {
					@Authorization (value = FooRestController.SWAGGER_AUTHENTICATION_SCHEME)
			}
	)
	@DeleteMapping (value = "/{id}",
	                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus (HttpStatus.OK)
	public SuccessRS deleteFoo (@PathVariable ("id") final Long id) {
		service.deleteById(id);
		return new SuccessRS();
	}


	protected FooDTO convertToDto (Foo item) {
		return modelMapper.map(item, FooDTO.class);
	}

	protected Foo convertToEntity (FooDTO item) {
		Foo entity = modelMapper.map(item, Foo.class);
		if (item.getId() != null) {
			Foo oldEntity = service.findById(entity.getId());
			entity.setVersion(oldEntity.getVersion());
		}

		return entity;
	}
}
