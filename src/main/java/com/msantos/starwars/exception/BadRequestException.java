package com.msantos.starwars.exception;

public class BadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 3526584079252090441L;

	public BadRequestException(String message) {
		super(message);
	}
}
