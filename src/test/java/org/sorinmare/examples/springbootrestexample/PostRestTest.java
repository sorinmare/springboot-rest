/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sorinmare.examples.springbootrestexample.rest.messages.LoginRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith (SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles ("dev")
public class PostRestTest extends AbstractTest {
	private static Logger LOG = LoggerFactory.getLogger(PostRestTest.class);

	@Autowired
	private MockMvc      mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void givenStartTest_whenPerformLogin_thenTokenReceivedAndInitIsCalled () throws Exception {
		if (super.initialized()) {
			LOG.debug("already initialized");
			return;
		}
		ResultActions resultActions = this.mockMvc.perform(post("/api/auth/login")
				                                                   .contentType(MediaType.APPLICATION_JSON)
				                                                   .content(
						                                                   "{\"username\":\"username\", \"password\":\"password\"}"))
		                                          .andExpect(jsonPath("$.token").isNotEmpty())
		                                          .andExpect(status().isOk());

		MvcResult result          = resultActions.andReturn();
		String    contentAsString = result.getResponse().getContentAsString();

		LoginRS response = objectMapper.readValue(contentAsString, LoginRS.class);
		super.setBearer("Bearer " + response.getToken());
		LOG.debug("Authorization: {}", super.getBearer());
		this.mockMvc.perform(post("/api/foos/init")
				                     .header("Authorization", super.getBearer())
				                     .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(jsonPath("$.success").exists())
		            .andExpect(status().isCreated());
	}

	@Test
	public void whenNotAuthorized_then403Received () throws Exception {
		this.mockMvc.perform(delete("/api/foos/-1"))
		            .andExpect(status().isForbidden());
	}

	@Test
	public void whenCreate_then200ReceivedAndSuccess () throws Exception {
		DateTimeFormatter fmt  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String            name = LocalDateTime.now().format(fmt);
		String            json = "{\"id\":\"-1\", \"name\":\"" + name + "\"}";

		this.mockMvc.perform(post("/api/foos/")
				                     .header("Authorization", super.getBearer())
				                     .contentType(MediaType.APPLICATION_JSON)
				                     .content(json))
		            .andExpect(jsonPath("$.name", equalTo(name)))
		            .andExpect(status().isCreated());
	}

	@Test
	public void given100_whenDelete_then200ReceivedAndSuccess () throws Exception {
		this.mockMvc.perform(delete("/api/foos/{id}", "100")
				                     .header("Authorization", super.getBearer())
				                     .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.success").exists());
	}

	@Test
	public void given1_whenUpdate_then200Received () throws Exception {
		DateTimeFormatter fmt  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String            name = LocalDateTime.now().format(fmt);
		String            json = "{\"id\":\"-1\", \"name\":\"" + name + "\"}";

		this.mockMvc.perform(put("/api/foos/{id}", "1")
				                     .header("Authorization", super.getBearer())
				                     .contentType(MediaType.APPLICATION_JSON)
				                     .content(json))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.name", equalTo(name)));
	}

	@Test
	public void whenTestSwagger_then200Received () throws Exception {
		this.mockMvc.perform(get("/swagger-ui.html"))
		            .andExpect(status().isOk());
	}

	@Test
	@Ignore ("For the moment H2C returns http status 404")
	public void whenTestH2C_then200Received () throws Exception {
		this.mockMvc.perform(get("/h2c/login.jsp"))
		            .andExpect(status().isOk());
	}

	@Test
	public void whenfindAll_then200ReceivedAndContentNotEmpty () throws Exception {
		this.mockMvc.perform(get("/api/foos/").contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.count").exists())
		            .andExpect(jsonPath("$.count").isNumber())
		            .andExpect(jsonPath("$.count", greaterThan(0)));
	}

	@Test
	public void given1_whenfindById_then200ReceivedAndContentNotEmpty () throws Exception {
		this.mockMvc.perform(get("/api/foos/{id}", "1")
				                     .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.id").exists())
		            .andExpect(jsonPath("$.id").isNumber())
		            .andExpect(jsonPath("$.id", equalTo(1)));
	}

	@Test
	public void given1_whenfindById_then200ReceivedAndGetEtag_whenIfNoneMatchETag_then304Received () throws Exception {
		String fooId = "1";
		ResultActions resultActions = this.mockMvc.perform(get("/api/foos/{id}", fooId)
				                                                   .contentType(MediaType.APPLICATION_JSON))
		                                          .andExpect(status().isOk())
		                                          .andExpect(jsonPath("$.id").exists())
		                                          .andExpect(jsonPath("$.id").isNumber())
		                                          .andExpect(jsonPath("$.id", equalTo(1)));
		MvcResult result = resultActions.andReturn();
		String    eTag   = result.getResponse().getHeader("ETag");
		LOG.debug("ETag = [{}]", eTag);
		this.mockMvc.perform(get("/api/foos/{id}", fooId)
				                     .contentType(MediaType.APPLICATION_JSON)
				                     .header("If-None-Match", eTag))
		            .andExpect(status().isNotModified());
	}

	@Test
	public void givenMinus1_whenfindById_then404Received () throws Exception {
		this.mockMvc.perform(get("/api/foos/{id}", "-1")
				                     .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isNotFound());
	}

	@Test
	public void whenfindPaginated_then200ReceivedAndContentNotEmpty () throws Exception {
		this.mockMvc.perform(get("/api/foos/paginated")
				                     .contentType(MediaType.APPLICATION_JSON)
				                     .param("page", "0")
				                     .param("size", "10"))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.pageable.pageSize").exists())
		            .andExpect(jsonPath("$.pageable.pageNumber").exists())
		            .andExpect(jsonPath("$.pageable.pageSize").isNumber())
		            .andExpect(jsonPath("$.pageable.pageNumber").isNumber())
		            .andExpect(jsonPath("$.pageable.pageSize", equalTo(10)))
		            .andExpect(jsonPath("$.pageable.pageNumber", equalTo(0)))
		            .andExpect(jsonPath("$._links").isNotEmpty());
	}

	@Test
	public void whenfindPageable_then200ReceivedAndContentNotEmpty () throws Exception {
		this.mockMvc.perform(get("/api/foos/pageable")
				                     .contentType(MediaType.APPLICATION_JSON)
				                     .param("page", "0")
				                     .param("size", "15")
				                     .param("sort", "id,desc"))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.pageable.pageSize").exists())
		            .andExpect(jsonPath("$.pageable.pageNumber").exists())
		            .andExpect(jsonPath("$.pageable.pageSize").isNumber())
		            .andExpect(jsonPath("$.pageable.pageNumber").isNumber())
		            .andExpect(jsonPath("$.pageable.pageSize", equalTo(15)))
		            .andExpect(jsonPath("$.pageable.pageNumber", equalTo(0)))
		            .andExpect(jsonPath("$._links").isNotEmpty());
	}
}
