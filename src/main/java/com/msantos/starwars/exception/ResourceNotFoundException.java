package com.msantos.starwars.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7810870563840181761L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
