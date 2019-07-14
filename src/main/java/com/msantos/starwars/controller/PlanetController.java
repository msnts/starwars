package com.msantos.starwars.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.msantos.starwars.model.entity.Planet;
import com.msantos.starwars.model.service.PlanetService;

@Validated
@RestController
@RequestMapping(path = "/planets", /*, consumes = "application/json; charset=utf-8",*/ produces = "application/json; charset=utf-8")
public class PlanetController {
	
	private static final String REGEX_OBJECT_ID = "^[a-z\\d]{24}$";
	
	@Autowired
	PlanetService planetService;
	
	@GetMapping
	public List<Planet> find() {
		return planetService.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public Planet findById(@Pattern(regexp = REGEX_OBJECT_ID) @PathVariable String id) {
		return planetService.findById(id);
	}
	
	@GetMapping(params = {"search"})
	public Planet findByName(@NotBlank @Size(min = 1, max = 20) @RequestParam("search") String name) {
		return planetService.findByName(name);
	}
		
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Planet add(@Valid @RequestBody Planet newPlanet) throws IOException {
		return planetService.add(newPlanet);
	}
	
	@PutMapping("/{id}")
	public Planet update(@RequestBody Planet newPlanet, @Pattern(regexp = REGEX_OBJECT_ID) @PathVariable String id) {
		return planetService.update(id, newPlanet);
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/json; charset=utf-8")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@Pattern(regexp = REGEX_OBJECT_ID) @PathVariable String id) {
		planetService.deleteById(id);
	}
}
