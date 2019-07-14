package com.msantos.starwars.exception;

public class PlanetNotFoundException extends ResourceNotFoundException {
	private static final long serialVersionUID = 5796832927623027208L;
	
	public PlanetNotFoundException(String message) {
		super(message);
	}

}
