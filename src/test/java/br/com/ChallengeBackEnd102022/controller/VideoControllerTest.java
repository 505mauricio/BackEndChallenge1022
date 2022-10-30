package br.com.ChallengeBackEnd102022.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.List;

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

import br.com.ChallengeBackEnd102022.model.Video;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VideoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
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
		assertEquals(2, videos.length);
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
		Video video = objectMapper.readValue(responseBody, Video.class);
		Video expectedVideo = new Video("Dad Battle - Friday Night Funkin OST",
				"Composed by Kawai Sprite: https://twitter.com/kawaisprite From the game Friday Night Funkin Play the game on Newgrounds! ","https://www.youtube.com/watch?v=w0WyKTSuX4U");
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
	
}
