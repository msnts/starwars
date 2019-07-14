package com.msantos.starwars.model.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.msantos.starwars.model.entity.Planet;

@Repository
public interface PlanetRepository extends MongoRepository<Planet, String> {
	public Optional<Planet> findByName(String name);
}