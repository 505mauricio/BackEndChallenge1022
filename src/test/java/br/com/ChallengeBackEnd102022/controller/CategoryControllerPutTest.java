package br.com.ChallengeBackEnd102022.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ChallengeBackEnd102022.model.Category;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoryControllerPutTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	
	@Test
	public void changeCategory() throws Exception {
		URI uri = new URI("/categorias/2");
		String json = "{\"titulo\":\"teal\", \"cor\":\"20c997\"}";
		
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.put(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		Category category = objectMapper.readValue(responseBody, Category.class);
		Category expectedCategory = new Category("teal","20c997");
		assertEquals(expectedCategory,category);
				
	}
	
	@Test
	public void changeInvalidTitleCategory() throws Exception {
		URI uri = new URI("/categorias/2");
		String json = "{\"titulo\":\"\", \"cor\":\"20c997\"}";
		
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.put(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(responseBody.contains("{\"field\":\"titulo\",\"error\":\"O campo titulo é obrigatório.\"}"));
			
	}
	
	@Test
	public void changeInvalidColorCategory() throws Exception {
		URI uri = new URI("/categorias/2");
		String json = "{\"titulo\":\"teal\", \"cor\":\"\"}";
		
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.put(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(responseBody.contains("{\"field\":\"cor\",\"error\":\"O campo cor deve ter estar no formato hexadecimal.\"}"));
		assertTrue(responseBody.contains("{\"field\":\"cor\",\"error\":\"O campo cor é obrigatório.\"}"));
			
	}
	
}