package br.com.ChallengeBackEnd102022.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

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

import br.com.ChallengeBackEnd102022.form.VideoForm;
import br.com.ChallengeBackEnd102022.model.Category;
import br.com.ChallengeBackEnd102022.model.Video;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoryControllerGetTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Test
	public void getAllCategories() throws Exception {
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
		Category[] categories = objectMapper.readValue(responseBody, Category[].class);
		assertEquals(12, categories.length);
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
