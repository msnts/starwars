package com.msantos.starwars.model.service;

import java.io.IOException;
import java.util.List;

import com.msantos.starwars.model.entity.Planet;

public interface PlanetService {

	public List<Planet> findAll();
	
	public Planet findById(String id);
	
	public Planet findByName(String name);
	
	public Integer GetNumberOfApparitions(String planetName);
	
	public Planet add(Planet planet) throws IOException;
	
	public Planet update(String id, Planet planet);
	
	public void deleteById(String id);
}
