package br.com.ChallengeBackEnd102022.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ChallengeBackEnd102022.model.Category;
import br.com.ChallengeBackEnd102022.model.Video;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerGetTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Test
	public void getCategories() throws Exception {
		URI uri = new URI("/categorias");
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.get(uri)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
		.andReturn();
		
		String responseBody = mvcResult.getResponse().getContentAsString();
		Pattern pattern = Pattern.compile("\\[(.*)\\]");
		Matcher matcher = pattern.matcher(responseBody);
		
		
		if(matcher.find(0)) {
			Category[] categories = objectMapper.readValue(matcher.group(0), Category[].class);
			assertEquals(5, categories.length);
		}
		else {
			fail();
		}
		
		
	}
	
	
	@Test
	public void getSecondCategory() throws Exception {
		URI uri = new URI("/categorias/2");
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.get(uri)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		Category category = objectMapper.readValue(responseBody, Category.class);
		Category expectedCategory = new Category("blue","0d6efd");
		assertEquals(expectedCategory,category);
		
	}
	
	@Test
	
	public void getAllVideosOfCategory() throws Exception{
		URI uri = new URI("/categorias/1/videos");
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.get(uri)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		Video[] videos = objectMapper.readValue(responseBody, Video[].class);
		assertEquals(4, videos.length);
	}
	
	@Test
	public void getInvalidCategoryId() throws Exception {
		URI uri = new URI("/categorias/42");
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.get(uri)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(404));		
	}
	
}
