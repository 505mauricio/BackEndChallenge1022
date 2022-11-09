package br.com.ChallengeBackEnd102022.controller;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
public class VideoControllerPutTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Test
	public void changeTitleVideo() throws Exception {
		URI uri = new URI("/videos/3");
		String json = "{\"titulo\":\"GoldLink\",\r\n"
				+ "\"descricao\": \"1 of 3 unreleased demos of unfinished ideas Download: https://soundcloud.com/squaaashclub/s...\",\r\n"
				+ "\"url\": \"https://www.youtube.com/watch?v=6PuE-Vpn84s\",\r\n"
				+ "\"categoriaId\": 1\r\n"
				+ "}";
		
		
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
		VideoForm videoForm = objectMapper.readValue(responseBody, VideoForm.class);
		Video video = videoForm.toVideo(categoryRepository);
		Category category = new Category("FREE","");
		category.setId(1l);
		Video expectedVideo = new Video("GoldLink",
				"1 of 3 unreleased demos of unfinished ideas Download: https://soundcloud.com/squaaashclub/s...","https://www.youtube.com/watch?v=6PuE-Vpn84s",category);
		assertEquals(expectedVideo,video);	
	}
	
	@Test
	public void changeDescriptionVideo() throws Exception {
		URI uri = new URI("/videos/3");
		String json = "{\"titulo\":\"GoldLink - G.Y.L.O\",\r\n"
				+ "\"descricao\": \"1 of 3 unreleased demos of unfinished ideas\",\r\n"
				+ "\"url\": \"https://www.youtube.com/watch?v=6PuE-Vpn84s\",\r\n"
				+ "\"categoriaId\": 1\r\n"
				+ "}";
		
		
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
		VideoForm videoForm = objectMapper.readValue(responseBody, VideoForm.class);
		Video video = videoForm.toVideo(categoryRepository);
		Category category = new Category("FREE","");
		category.setId(1l);
		Video expectedVideo = new Video("GoldLink - G.Y.L.O",
				"1 of 3 unreleased demos of unfinished ideas","https://www.youtube.com/watch?v=6PuE-Vpn84s",category);
		assertEquals(expectedVideo,video);				
	}
	
	@Test
	public void changeUrlVideo() throws Exception {
		URI uri = new URI("/videos/3");
		String json = "{\"titulo\":\"GoldLink - G.Y.L.O\",\r\n"
				+ "\"descricao\": \"1 of 3 unreleased demos of unfinished ideas Download: https://soundcloud.com/squaaashclub/s...\",\r\n"
				+ "\"url\": \"https://www.youtube.com/watch?v=6PuE-Vpn84s&list=RD6PuE-Vpn84s&start_radio=1\",\r\n"
				+ "\"categoriaId\": 1\r\n"
				+ "}";
		
		
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
		VideoForm videoForm = objectMapper.readValue(responseBody, VideoForm.class);
		Video video = videoForm.toVideo(categoryRepository);
		Category category = new Category("FREE","");
		category.setId(1l);
		Video expectedVideo = new Video("GoldLink - G.Y.L.O",
				"1 of 3 unreleased demos of unfinished ideas Download: https://soundcloud.com/squaaashclub/s...","https://www.youtube.com/watch?v=6PuE-Vpn84s&list=RD6PuE-Vpn84s&start_radio=1",category);
		assertEquals(expectedVideo,video);				
	}
	
	@Test
	public void changeCategoryIdVideo() throws Exception {
		URI uri = new URI("/videos/3");
		String json = "{\"titulo\":\"GoldLink - G.Y.L.O\",\r\n"
				+ "\"descricao\": \"1 of 3 unreleased demos of unfinished ideas Download: https://soundcloud.com/squaaashclub/s...\",\r\n"
				+ "\"url\": \"https://www.youtube.com/watch?v=6PuE-Vpn84s\",\r\n"
				+ "\"categoriaId\": 2\r\n"
				+ "}";
		
		
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
		VideoForm videoForm = objectMapper.readValue(responseBody, VideoForm.class);
		Video video = videoForm.toVideo(categoryRepository);
		Category category = new Category("blue","0d6efd");
		category.setId(2l);
		Video expectedVideo = new Video("GoldLink - G.Y.L.O",
				"1 of 3 unreleased demos of unfinished ideas Download: https://soundcloud.com/squaaashclub/s...","https://www.youtube.com/watch?v=6PuE-Vpn84s",category);
		assertEquals(expectedVideo,video);				
	}
	
	@Test
	public void invalidTitleVideoPut() throws Exception {
		URI uri = new URI("/videos/3");
		String json = "{\"titulo\":\"\",\r\n"
				+ "\"descricao\": \"1 of 3 unreleased demos of unfinished ideas Download: https://soundcloud.com/squaaashclub/s...\",\r\n"
				+ "\"url\": \"https://www.youtube.com/watch?v=6PuE-Vpn84s\",\r\n"
				+ "\"categoriaId\": 2\r\n"
				+ "}";
		
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.put(uri)
				.content(json)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(responseBody.contains("{\"field\":\"titulo\",\"error\":\"O campo titulo é obrigatório.\"}"));
		assertTrue(responseBody.contains("{\"field\":\"titulo\",\"error\":\"O campo titulo deve ter no mínimo 5 caracteres.\"}"));				
	}
	
	@Test
	public void invalidDescriptionVideoPut() throws Exception {
		URI uri = new URI("/videos/3");
		String json = "{\"titulo\":\"GoldLink - G.Y.L.O\",\r\n"
				+ "\"descricao\": \"\",\r\n"
				+ "\"url\": \"https://www.youtube.com/watch?v=6PuE-Vpn84s\",\r\n"
				+ "\"categoriaId\": 2\r\n"
				+ "}";
		
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.put(uri)
				.content(json)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(responseBody.contains("{\"field\":\"descricao\",\"error\":\"O campo descrição é obrigatório.\"}"));
		assertTrue(responseBody.contains("{\"field\":\"descricao\",\"error\":\"O campo descrição deve ter no mínimo 10 caracteres.\"}"));				
	}
	
	@Test
	public void invalidUrlVideoPut() throws Exception {
		URI uri = new URI("/videos/3");
		String json = "{\"titulo\":\"GoldLink - G.Y.L.O\",\r\n"
				+ "\"descricao\": \"1 of 3 unreleased demos of unfinished ideas Download: https://soundcloud.com/squaaashclub/s...\",\r\n"
				+ "\"url\": \"\",\r\n"
				+ "\"categoriaId\": 2\r\n"
				+ "}";
		
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.put(uri)
				.content(json)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(responseBody.contains("{\"field\":\"url\",\"error\":\"O campo url é obrigatório.\"}"));
		assertTrue(responseBody.contains("{\"field\":\"url\",\"error\":\"O campo url deve ter no mínimo 5 caracteres.\"}"));				
	}
	
	@Test
	public void invalidCategoryIdVideoPut() throws Exception {
		URI uri = new URI("/videos/3");
		String json = "{\"titulo\":\"GoldLink - G.Y.L.O\",\r\n"
				+ "\"descricao\": \"1 of 3 unreleased demos of unfinished ideas Download: https://soundcloud.com/squaaashclub/s...\",\r\n"
				+ "\"url\": \"https://www.youtube.com/watch?v=6PuE-Vpn84s\",\r\n"
				+ "\"categoriaId\": \"\"\r\n"
				+ "}";
		
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.put(uri)
				.content(json)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(responseBody.contains("{\"field\":\"categoriaId\",\"error\":\"O campo categoriaId é obrigatório.\"}"));			
	}
	
}
