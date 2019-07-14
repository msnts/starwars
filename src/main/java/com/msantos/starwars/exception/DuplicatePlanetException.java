package com.msantos.starwars.exception;

public class DuplicatePlanetException extends BadRequestException {

	private static final long serialVersionUID = -1657889672024917348L;

	public DuplicatePlanetException(String message) {
		super(message);
	}
}
