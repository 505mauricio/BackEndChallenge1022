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
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class VideoControllerPostTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	
	
	@Test
	public void createVideo() throws Exception {
		URI uri = new URI("/videos");
		String json = "{\"titulo\":\"Moe Shop - Cerise\",\r\n"
				+ "\"descricao\": \"Moe Shop - Cerise Stream/Download: https://moeshop.fanlink.to/cerise Produced by Moe Shop Art by Yoneyama Mai Design by GraphersRock\",\r\n"
				+ "\"url\": \"https://www.youtube.com/watch?v=y-27yTRTJtE&list=RDy-27yTRTJtE\",\r\n"
				+ "\"categoriaId\": 1\r\n"
				+ "}";
		
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.characterEncoding("utf-8")
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
		Video expectedVideo = new Video("Moe Shop - Cerise",
				"Moe Shop - Cerise Stream/Download: https://moeshop.fanlink.to/cerise Produced by Moe Shop Art by Yoneyama Mai Design by GraphersRock","https://www.youtube.com/watch?v=y-27yTRTJtE&list=RDy-27yTRTJtE",category);

		assertEquals(expectedVideo,video);		
	}
	
	
	@Test
	public void invalidTitleVideoPost() throws Exception {
		URI uri = new URI("/videos");
		String json = "{\"titulo\":\"\",\r\n"
				+ "\"descricao\": \"Moe Shop - Cerise Stream/Download: https://moeshop.fanlink.to/cerise Produced by Moe Shop Art by Yoneyama Mai Design by GraphersRock\",\r\n"
				+ "\"url\": \"https://www.youtube.com/watch?v=y-27yTRTJtE&list=RDy-27yTRTJtE\",\r\n"
				+ "\"categoriaId\": 1\r\n"
				+ "}";
		
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(responseBody.contains("O campo titulo ?? obrigat??rio."));
		assertTrue(responseBody.contains("O campo titulo deve ter no m??nimo 5 caracteres."));		
	}
	
	
	@Test
	public void invalidDescriptionVideoPost() throws Exception {
		URI uri = new URI("/videos");
		String json = "{\"titulo\":\"Moe Shop - Cerise\",\r\n"
				+ "\"descricao\": \"\",\r\n"
				+ "\"url\": \"https://www.youtube.com/watch?v=y-27yTRTJtE&list=RDy-27yTRTJtE\",\r\n"
				+ "\"categoriaId\": 1\r\n"
				+ "}";
		
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(responseBody.contains("O campo descri????o deve ter no m??nimo 10 caracteres."));
		assertTrue(responseBody.contains("O campo descri????o ?? obrigat??rio."));
	}
	
	@Test
	public void invalidUrlVideoPost() throws Exception {
		URI uri = new URI("/videos");
		String json = "{\"titulo\":\"Moe Shop - Cerise\",\r\n"
				+ "\"descricao\": \"Moe Shop - Cerise Stream/Download: https://moeshop.fanlink.to/cerise Produced by Moe Shop Art by Yoneyama Mai Design by GraphersRock\",\r\n"
				+ "\"url\": \"\",\r\n"
				+ "\"categoriaId\": 1\r\n"
				+ "}";
		
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(responseBody.contains("O campo url ?? obrigat??rio."));
		assertTrue(responseBody.contains("O campo url deve ter no m??nimo 5 caracteres."));
	}
	
	@Test
	public void blankCategoriaIdVideoPost() throws Exception {
		URI uri = new URI("/videos");
		String json = "{\"titulo\":\"Moe Shop - Cerise\",\r\n"
				+ "\"descricao\": \"Moe Shop - Cerise Stream/Download: https://moeshop.fanlink.to/cerise Produced by Moe Shop Art by Yoneyama Mai Design by GraphersRock\",\r\n"
				+ "\"url\": \"\",\r\n"
				+ "\"categoriaId\": \"\"\r\n"
				+ "}";
		
		
		MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(responseBody.contains("O campo categoriaId ?? obrigat??rio."));
		}
	
}
