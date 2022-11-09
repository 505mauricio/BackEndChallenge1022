package br.com.ChallengeBackEnd102022.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.nio.charset.StandardCharsets;

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

import br.com.ChallengeBackEnd102022.form.VideoForm;
import br.com.ChallengeBackEnd102022.model.Category;
import br.com.ChallengeBackEnd102022.model.Video;
import br.com.ChallengeBackEnd102022.repository.CategoryRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VideoControllerGetTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Test
	public void getAllMovies() throws Exception {
		URI uri = new URI("/videos");
		
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
	public void getAllMoviesWithDadOnTitle() throws Exception {
		URI uri = new URI("/videos/?search=Dad");
		
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
		assertEquals(1, videos.length);
	}
	
	
	@Test
	public void getFirstMovie() throws Exception {
		URI uri = new URI("/videos/1");
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.get(uri)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		VideoForm videoForm = objectMapper.readValue(responseBody, VideoForm.class);
		Video video = videoForm.toVideo(categoryRepository);
		
		Category category = new Category("FREE","");
		category.setId(1l);
		Video expectedVideo = new Video("Dad Battle - Friday Night Funkin OST",
				"Composed by Kawai Sprite: https://twitter.com/kawaisprite From the game Friday Night Funkin Play the game on Newgrounds!","https://www.youtube.com/watch?v=w0WyKTSuX4U",category);
		assertEquals(expectedVideo,video);	
	}
	
	@Test
	public void getInvalidId() throws Exception {
		URI uri = new URI("/videos/42");
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.get(uri)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(404));	
	}
	
	
	@Test
	public void titleSearchVideo() throws Exception {
		URI uri = new URI("/videos/?search=Dad");		
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.get(uri)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		VideoForm[] videoForm = objectMapper.readValue(responseBody, VideoForm[].class);
		Video video = videoForm[0].toVideo(categoryRepository);
		Category category = new Category("FREE","");
		category.setId(1l);
		Video expectedVideo = new Video("Dad Battle - Friday Night Funkin OST",
				"Composed by Kawai Sprite: https://twitter.com/kawaisprite From the game Friday Night Funkin Play the game on Newgrounds!","https://www.youtube.com/watch?v=w0WyKTSuX4U",category);	
		assertEquals(expectedVideo,video);
	}
}
