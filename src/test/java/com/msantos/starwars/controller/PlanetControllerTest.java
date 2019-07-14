package com.msantos.starwars.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import com.msantos.starwars.model.entity.Planet;
import com.msantos.starwars.model.repository.PlanetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PlanetControllerTest {
	
	private static final String HOTH_ID = "5d2b7b4740a27441f834b778";

	private static final String YAVIN_IV_ID = "5d2b5bf940a2741c04643aca";

	private static final String ALDERAAN_ID = "5d2b2ab440a2742c5c1b067c";

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private PlanetRepository planetRepository;

	@Before
	public void setUp() throws Exception {
		planetRepository.deleteAll();
		
		Planet planet = Planet.builder().id(ALDERAAN_ID).name("Alderaan").climate("temperate").terrain("grasslands, mountains").build();
		
		planetRepository.save(planet);
		
		planet = Planet.builder().id(YAVIN_IV_ID).name("Yavin IV").climate("temperate, tropical").terrain("jungle, rainforests").build();
		
		planetRepository.save(planet);
		
		planet = Planet.builder().id(HOTH_ID).name("Hoth").climate("frozen").terrain("tundra").build();
		
		planetRepository.save(planet);
	}

	@Test
	public void should_create_planet_successfully() throws Exception {
		String content = "{\"name\": \"Dagobah\", \"climate\":\"temperate\", \"terrain\":\"plan\"}";
		
		mockMvc.perform(post("/planets").content(content).contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isCreated());
	}
	
	@Test
	public void should_validate_planet_successfully() throws Exception {
		String content = "{\"name\": \"\", \"climate\":\"\", \"terrain\":\"\"}";
		
		mockMvc.perform(post("/planets").content(content).contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors[*]", hasItems(
					"terrain: size must be between 1 and 20",
					"climate: size must be between 1 and 20",
					"name: size must be between 1 and 20",
					"terrain: must not be empty",
					"name: must not be empty",
					"climate: must not be empty")));
	}
	
	@Test
	public void should_block_duplicate_planet_creation() throws Exception {
		String content = "{\"name\": \"Yavin IV\", \"climate\":\"temperate\", \"terrain\":\"plan\"}";

		mockMvc.perform(post("/planets").content(content).contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isBadRequest());	
	}
	
	@Test
	public void should_list_planets() throws Exception {
		mockMvc.perform(get("/planets"))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)))
			.andExpect(jsonPath("$[0].id", is(ALDERAAN_ID)));
	}
	
	@Test
	public void should_find_planet_by_id() throws Exception {
		mockMvc.perform(get("/planets/" + YAVIN_IV_ID))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(YAVIN_IV_ID)));
	}
	
	@Test
	public void should_block_invalid_id_in_find_by_id() throws Exception {
		mockMvc.perform(get("/planets/kdafjaçsdkfjaçs"))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void should_find_planet_by_name() throws Exception {
		mockMvc.perform(get("/planets?search=Yavin IV"))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(YAVIN_IV_ID)));
	}
	
	@Test
	public void should_block_invalid_name_in_find_by_name() throws Exception {
		mockMvc.perform(get("/planets?search="))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void should_update_planet() throws Exception {
		String content = "{\"name\": \"Yavin IV\", \"climate\":\"temperate\", \"terrain\":\"plan\"}";
		
		mockMvc.perform(put("/planets/" + YAVIN_IV_ID).content(content).contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk());
	}
	
	@Test
	public void should_block_duplicate_planet_in_update() throws Exception {
		String content = "{\"name\": \"Yavin IV\", \"climate\":\"temperate\", \"terrain\":\"plan\"}";
		
		mockMvc.perform(put("/planets/" + HOTH_ID).content(content).contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void should_delete_planet() throws Exception {
		mockMvc.perform(delete("/planets/" + ALDERAAN_ID))
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void should_validate_id_in_delete_planet() throws Exception {
		mockMvc.perform(get("/planets/fkasdçjfkajç"))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isBadRequest());
	}
}
