package com.msantos.starwars.model.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.msantos.starwars.model.entity.Planet;
import com.msantos.starwars.model.repository.PlanetRepository;
import com.msantos.starwars.model.service.impl.PlanetServiceImpl;

@RunWith(SpringRunner.class)
public class PlanetServiceImplTest {
	
	@Mock
	private PlanetRepository mockPlanetRepository;
	
	@InjectMocks
	private PlanetServiceImpl planetService;
	
	@Before
	public void loadDataBase() {
		
		Planet planet = Planet.builder().id("5d2b2ab440a2742c5c1b067c").name("Alderaan").climate("temperate").terrain("grasslands, mountains").build();
		
		when(mockPlanetRepository.findById("5d2b2ab440a2742c5c1b067c")).thenReturn(Optional.of(planet));
		
	}

	@Test
	public void should_find_id() {
		Planet planet = planetService.findById("5d2b2ab440a2742c5c1b067c");
		
		assertTrue("5d2b2ab440a2742c5c1b067c".equals(planet.getId()));
	}

}
