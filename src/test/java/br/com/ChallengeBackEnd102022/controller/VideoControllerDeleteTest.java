package br.com.ChallengeBackEnd102022.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VideoControllerDeleteTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@Test
	public void deleteVideo() throws Exception {
		URI uri = new URI("/videos/4");

		mockMvc
		.perform(MockMvcRequestBuilders
				.delete(uri)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(200));	
	}
	
	@Test
	public void deleteInvalidVideo() throws Exception {
		URI uri = new URI("/videos/42");

		mockMvc
		.perform(MockMvcRequestBuilders
				.delete(uri)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(404));
	}
	
}
