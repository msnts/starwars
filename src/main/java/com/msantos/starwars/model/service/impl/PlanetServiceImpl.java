package com.msantos.starwars.model.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msantos.starwars.exception.DuplicatePlanetException;
import com.msantos.starwars.exception.PlanetNotFoundException;
import com.msantos.starwars.exception.RemoteRequestException;
import com.msantos.starwars.model.entity.Planet;
import com.msantos.starwars.model.repository.PlanetRepository;
import com.msantos.starwars.model.service.PlanetService;

@Service
public class PlanetServiceImpl implements PlanetService {
	
	private static final String SWAPI_PLANETS = "https://swapi.co/api/planets";
	
	@Autowired
    private PlanetRepository planetRepository;
	
	@Lazy
	@Autowired
    RestTemplate restTemplate;
	
	public List<Planet> findAll() {
		return planetRepository.findAll();
	}
	
	public Planet findById(String id) {
		return planetRepository.findById(id).
				orElseThrow(() -> new PlanetNotFoundException("Planet id not found: " + id));
	}
	
	public Planet findByName(String name) {
		return planetRepository.findByName(name).
				orElseThrow(() -> new PlanetNotFoundException("Planet name not found: " + name));
	}
	
	public Integer GetNumberOfApparitions(String planetName) {
		ObjectMapper objectMapper = new ObjectMapper();
		String response;
		JsonNode jsonNode;
		
		try {
			response = restTemplate.getForObject(SWAPI_PLANETS + "/?search=" + planetName, String.class);
		} catch(Exception e) {
			//Registrar em log a ocorrência do erro para tratamento dos motivos
			throw new RemoteRequestException("SWAPI unreachable");
		}
		
		try {
			jsonNode = objectMapper.readTree(response);
			
			return jsonNode.get("results").get(0).get("films").size();
		} catch (IOException e) {
			/* 
			 * Formato de retorno diferente do esperado
			 * Registrar em log a ocorrência do erro
			*/
		}
		
		return Integer.valueOf(0);
	}
	
	public Planet add(Planet planet) {
		
		planetRepository.findByName(planet.getName()).
			ifPresent(e -> {
				throw new DuplicatePlanetException("A planet duplicate is not allowed or expected");
			});
		
		Integer number = this.GetNumberOfApparitions(planet.getName());
		
		planet.setNumberOfApparitions(number); 
		
		return planetRepository.insert(planet);
	}

	public Planet update(String id, Planet planet) {
		
		Planet oldPlanet = planetRepository.findById(id)
				.orElseThrow(() -> new PlanetNotFoundException("Planet id not found: " + id));
		
		planetRepository.findByName(planet.getName()).
			ifPresent(e -> {
				if (!id.equals(e.getId())) {
					throw new DuplicatePlanetException("A planet duplicate is not allowed or expected");
				}
			});
		
		oldPlanet.setName(planet.getName());
		oldPlanet.setClimate(planet.getClimate());
		oldPlanet.setTerrain(planet.getTerrain());
		
		return planetRepository.save(oldPlanet);
	}

	public void deleteById(String id) {
		if (!planetRepository.existsById(id)) {
			throw new PlanetNotFoundException("Planet id not found: " + id);
		}
		planetRepository.deleteById(id);		
	}

}
